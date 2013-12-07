package model.serialization.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Barcode;
import model.NonEmptyString;
import model.Unit;

public class JDBCWrapper implements Closeable {
	private Connection connection;
	private String path;
	private PreparedStatement statement;

	public JDBCWrapper(String path) {
		this.path = path;
		try {
			connection = createValidConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement = connection.prepareStatement("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JDBCWrapper() {
		this("./InventoryTracker.sqlite");
	}

	private Connection createValidConnection() throws SQLException, IOException
	{
		File sqliteFile = new File(path);
		if (!sqliteFile.exists())
		{
			return resetSQLFile(sqliteFile);
		}
		String MIMEType = Files.probeContentType(sqliteFile.toPath());
		if (!MIMEType.matches("^.*sqlite3.*$"))
		{
			return resetSQLFile(sqliteFile);
		}
		Connection returned = createConnection(sqliteFile);
		HashSet<String> neededTables = new HashSet<String>();
		HashSet<String> foundTables = new HashSet<String>();
		neededTables.add("Item");
		neededTables.add("Product");
		neededTables.add("Model");
		neededTables.add("ProductContainer");
		neededTables.add("ProductContainerProductRelation");
		//neededTables.add("UnitEnum");

		Statement statement = returned.createStatement();
		ResultSet rows = statement.executeQuery(
				"SELECT name FROM sqlite_master where type='table';");
		ResultSetMetaData meta = rows.getMetaData();
		while (rows.next())
		{
			String name;
			name = rows.getString("name");
			if (!name.matches(".*sqlite.*") &&
					neededTables.contains(name))
			{
				foundTables.add(name);
			}
			else
			{
				System.err.println("Found extraneous table '" + name + "'.");
				System.err.println("Resetting database.");
				returned.close();
				return resetSQLFile(sqliteFile);
			}
		}
		if (foundTables.size() != neededTables.size())
		{
			System.err.println("Didn't find all the tables needed.");
			System.err.println("Found tables: " + foundTables);
			System.err.println("Needed tables: " + neededTables);
			System.err.println("Resetting tables.");
			returned.close();
			return resetSQLFile(sqliteFile);
		}
		return returned;
	}


	private Connection createConnection(File sqliteFile) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String url = "jdbc:sqlite:" + sqliteFile.getAbsolutePath();
		Connection returned = null; 
		try {
			returned = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return returned;
	}

	private Connection resetSQLFile(File sqliteFile) throws IOException, SQLException {
		if (sqliteFile.exists())
		{
			sqliteFile.delete();
		}
		try {
			sqliteFile.createNewFile();
		} catch (IOException e) {
			System.err.println("Could not create a new database file!");
			e.printStackTrace();
			System.exit(1);
		}
		Connection returned = createConnection(sqliteFile);
		File sqlFile = new File("./sqlStatements.sql");
		System.out.println(sqlFile.exists());

		FileReader reader = null;
		try {
			reader = new FileReader(sqlFile);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find '" + sqlFile.getAbsolutePath() +
					"', but we just created it. This is a problem.");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(sqliteFile.getAbsolutePath());
		char [] fileChars = new char[(int) sqlFile.length()];
		reader.read(fileChars);
		String sql = new String(fileChars);
		String sqls[] = sql.split("[;]");
		reader.close();
		for(String s : sqls) {
			try {
			Statement resetDatabase = returned.createStatement();
			resetDatabase.executeUpdate(s+";");
			resetDatabase.close();
			} catch(SQLException e) {
				if(!e.getMessage().equals("not an error")) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		return returned;
	}

	public void executeUpdate(String sql) {
		try {
			statement.close();
			Statement statement2 = connection.createStatement();
			//statement = connection.prepareStatement(sql);
			statement2.executeUpdate(sql);
			statement2.close();
			//statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet executeQuery(String sql) {
		try {
			//statement.close();
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private void set(PreparedStatement statement, int i, Object object) throws SQLException {
		++i;
		if(object instanceof String) {
			statement.setString(i, (String) object);
		} else if(object instanceof Double) {
			statement.setDouble(i, (Double) object);
		} else if(object instanceof Integer) {
			statement.setInt(i, (Integer) object);
		} else if(object instanceof java.util.Date) {
			statement.setDate(i, new java.sql.Date(((java.util.Date) object).getTime()));
		} else if(object instanceof model.AbstractDateTime) {
			statement.setDate(i, new java.sql.Date(
					((model.AbstractDateTime) object).toJavaUtilDate().getTime()));
		} else if(object instanceof Unit) {
			statement.setString(i, ((Unit) object).toString());
		} else if(object instanceof Barcode) {
			statement.setString(i, ((Barcode) object).getBarcode());
		} else if(object instanceof NonEmptyString) {
			statement.setString(i, ((NonEmptyString) object).getValue());
		} else if(object instanceof Boolean) {
			statement.setBoolean(i, (boolean) object);
		} else {
			if(object == null) {
				new Exception("Error: JDBCWrapper got a null somehow.").printStackTrace();
			} else {
				new Exception("Error: JDBCWrapper does not accept class "
			+ object.getClass().getCanonicalName() + " " + i).printStackTrace();
			}
				System.exit(1);
		}
	}

	/**
	 * @param table			Name of the table to insert a new entry into
	 * @param columnNames	List of columns in the table
	 * @param columnValues	List of values for those columns
	 */
	public void insert(String table, List<String> columnNames, List<Object> columnValues) {
		System.out.println(columnValues);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append(" (");
			boolean first = true;
			for(String name : columnNames) {
				if(first) {
					first = false;
				} else {
					sql.append(", ");
				}
				sql.append(name);
			};
			sql.append(") VALUES (");
			first = true;
			for(int i=0; i<columnValues.size(); ++i) {
				if(first) {
					first = false;
				} else {
					sql.append(", ");
				}
				if(columnValues.get(i) == null) {
					sql.append("null");
				} else {
					sql.append("?");
				}
			};
			sql.append(");");
			//statement.close();
			statement = connection.prepareStatement(sql.toString());
			int i=0;
			for(Object value : columnValues) {
				if(value != null) {
					set(statement, i, value);
					++i;
				}
			};
			statement.executeUpdate();
			//statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param table		Name of the table to insert a new entry into
	 * @param columns	List of elements in the entry.
	 * 					If the value is null, give the class instead.
	 */
	public void insert(String table, List<Object> columns) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append(" VALUES (");
			for(int i=0; i<columns.size(); ++i) {
				if(i != 0) {
					sql.append(", ");
				}
				if(columns.get(i) == null) {
					sql.append("null");
				} else {
					sql.append("?");
				}
			};
			sql.append(");");
			//statement.close();
			statement = connection.prepareStatement(sql.toString());
			int i=0;
			for(Object column : columns) {
				if(column != null) {
					set(statement, i, column);
					++i;
				}
			};
			statement.executeUpdate();
			//statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param table			Name of table to search
	 * @param columnName	Name of column to search
	 * @param columnValue	Name of the value of that column.
	 * 						If the value is null, give the class instead.
	 * 
	 * @return				ResultSet corresponding to the result of this search
	 */
	public ResultSet query(String table, String columnName, Object columnValue) {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add(columnName);
		ArrayList<Object> columnValues = new ArrayList<Object>();
		columnValues.add(columnValue);
		return query(table, columnNames, columnValues);
	}

	/**
	 * @param table			Name of table to search
	 * @param columnName	Names of columns to search
	 * @param columnValue	Names of the values of those columns.
	 * 						If the value is null, give the class instead.
	 * 
	 * @return				ResultSet corresponding to the result of this search
	 */
	public ResultSet query(String table, List<String> columnNames, List<Object> columnValues) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ");
			sql.append(table);
			for(int i=0; i<columnNames.size(); ++i) {
				if(i == 0) {
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}
				sql.append(columnNames.get(i));
				if(columnValues.get(i) == null) {
					sql.append(" is null");
				} else {
					sql.append("=?");
				}
			}
			sql.append(';');
			//statement.close();
			statement = connection.prepareStatement(sql.toString());
			int i = 0;
			for(Object value : columnValues) {
				if(value != null) {
					set(statement, i, value);
					++i;
				}
			}
			ResultSet results = statement.executeQuery();
			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param table
	 * @return			A ResultSet containing the entire table
	 */
	public ResultSet queryAll(String table) {
		return query(table, new ArrayList<String>(), new ArrayList<Object>());
	}

	/**
	 * @param table				Table to update
	 * @param columnNames		List of columns to update
	 * @param columnValues		List of values for those columns.
	 * 							If the value is null, give the class instead.
	 * @param identifierName	Name of identifier to find the row that needs to be updated
	 * @param identifierValue	Value in that column.
	 * 							If the value is null, give the class instead.
	 */
	public void update(String table, List<String> columnNames, List<Object> columnValues,
			String identifierName, Object identifierValue) {
		ArrayList<String> identifierNames = new ArrayList<String>();
		identifierNames.add(identifierName);
		ArrayList<Object> identifierValues = new ArrayList<Object>();
		identifierValues.add(identifierValue);
		update(table, columnNames, columnValues, identifierNames, identifierValues);
	}

	/**
	 * @param table				Table to update
	 * @param columnNames		List of columns to update
	 * @param columnValues		List of values for those columns.
	 * 							If the value is null, give the class instead.
	 * @param identifierNames	Names of identifiers to find the row that needs to be updated
	 * @param identifierValues	Values in those column.
	 * 							If the value is null, give the class instead.
	 */
	public void update(String table, List<String> columnNames, List<Object> columnValues,
			List<String> identifierNames, List<Object> identifierValues) {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ");
			sql.append(table);
			int i;
			for(i=0; i<columnNames.size(); ++i) {
				if(i == 0) {
					sql.append(" SET ");
				} else {
					sql.append(", ");
				}
				sql.append(columnNames.get(i));
				if(columnValues.get(i) == null) {
					sql.append("=null");
				} else {
					sql.append("=?");
				}
			}
			for(i=0; i<identifierNames.size(); ++i) {
				if(i == 0) {
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}
				sql.append(identifierNames.get(i));
				if(columnValues.get(i) == null) {
					sql.append(" is null");
				} else {
					sql.append("=?");
				}
			}
			sql.append(';');
			//statement.close();
			statement = connection.prepareStatement(sql.toString());
			i = 0;
			for(Object value : columnValues) {
				if(value != null) {
					set(statement, i, value);
					++i;
				}
			}
			for(Object value : identifierValues) {
				if(value != null) {
					set(statement, i, value);
					++i;
				}
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*try {
				//statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	/**
	 * @param table				Table to delete an entry from
	 * @param identifierName	Name of the column used as an identifier
	 * @param videntifierValue	value of that column.
	 * 							If the value is null, give the class instead.
	 */
	public void delete(String table, String identifierName, Object identifierValue) {
		ArrayList<String> identifierNames = new ArrayList<String>();
		identifierNames.add(identifierName);
		ArrayList<Object> identifierValues = new ArrayList<Object>();
		identifierValues.add(identifierValue);
		delete(table, identifierNames, identifierValues);
	}

	/**
	 * @param table				Table to delete an entry from
	 * @param identifierNames	Name of the columns used as an identifier
	 * @param videntifierValues	values of those columns
	 */
	public void delete(String table, List<String> identifierNames,
			List<Object> identifierValues) {
		try {
			//statement.close();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM ");
			sql.append(table);
			for(int i=0; i<identifierNames.size(); ++i) {
				if(i == 0) {
					sql.append(" WHERE ");
				} else {
					sql.append(" AND ");
				}
				sql.append(identifierNames.get(i));
				if(identifierValues.get(i) == null) {
					sql.append(" is null");
				} else {
					sql.append("=?");
				}
			}
			sql.append(';');
			statement = connection.prepareStatement(sql.toString());
			int i = 0;
			for(Object value : identifierValues) {
				if(value != null) {
					set(statement, i, value);
					++i;
				}
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*try {
				//statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	@Override
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*private class ResultIterator implements Iterator<ResultSet>, Iterable<ResultSet> {
		private ResultSet resultSet;

		public ResultIterator(ResultSet resultSet) {
			this.resultSet = resultSet;
		}

		@Override
		public boolean hasNext() {
			try {
				return resultSet.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public ResultSet next() {
			return resultSet;
		}

		@Override
		public void remove() {
		}

		@Override
		public Iterator<ResultSet> iterator() {
			return this;
		}

	}*/

}
