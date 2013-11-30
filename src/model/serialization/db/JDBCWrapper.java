package model.serialization.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class JDBCWrapper {
	private Connection connection;
	public JDBCWrapper() {
		try {
			connection = DriverManager.getConnection("database.sql");
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*	public void executeUpdate(String sql) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.executeUpdate(sql);
			connection.commit();
			connection.setAutoCommit(false);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
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
		} else if(object instanceof model.Date) {
			statement.setDate(i, new java.sql.Date(((model.Date) object).toJavaUtilDate().getTime()));
		} else {
			System.err.println("Error: JDBCWrapper does not accept class " + object.getClass().getCanonicalName());
		}
	}
	
	/**
	 * @param table			Name of the table to insert a new entry into
	 * @param columnNames	List of columns in the table
	 * @param columnValues	List of values for those columns
	 */
	public void insert(String table, List<String> columnNames, List<Object> columnValues) {
		//TODO
	}
	
	/**
	 * @param table			Name of the table to insert a new entry into
	 * @param columnNames	List of columns in the table
	 * @param columnValues	List of values for those columns
	 * @return				Auto-generated ID
	 */
	public int insertAndGetID(String table, List<String> columnNames, List<Object> columnValues) {
		return 0;
	}
	
	/**
	 * @param table		Name of the table to insert a new entry into
	 * @param columns	List of elements in the entry
	 */
	public void insert(String table, List<Object> columns) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append(" VALUES (");
			for(int i=0; i<columns.size(); ++i) {
				sql.append("?, ");
			};
			sql.append(");");
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			int i=0;
			for(Object column : columns) {
				set(statement, i, column);
				++i;
			};
			statement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(false);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param table			Name of table to search
	 * @param columnName	Name of column to search
	 * @param columnValue	Name of the value of that column
	 * 
	 * @return				ResultSet corresponding to the result of this search
	 */
	public ResultSet query(String table, String columnName, Object columnValue) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ");
			sql.append(table);
			sql.append(" WHERE ");
			sql.append(columnName);
			sql.append("=?;");
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			set(statement, 0, columnValue);
			ResultSet results = statement.executeQuery();
			connection.commit();
			connection.setAutoCommit(false);
			statement.close();
			return results;	//TODO: Does the result set keep working after everything is closed?
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param table			Name of table to search
	 * @param columnName	Names of columns to search
	 * @param columnValue	Names of the values of those columns
	 * 
	 * @return				ResultSet corresponding to the result of this search
	 */
	public ResultSet query(String table, List<String> ColumnNames, List<Object> ColumnValues) {
		//TODO
		return null;
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
