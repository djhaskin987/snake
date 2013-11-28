package model.serialization.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.nio.file.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DAOFactory implements IDAOFactory {
	private Connection dbConnection;
	
	private Connection createValidConnection() throws SQLException, IOException
	{
        File sqliteFile = new File("./inventory-tracker.sqlite");
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
		neededTables.add("UnitEnum");
		
        Statement statement = returned.createStatement();
        ResultSet rows = statement.executeQuery("SELECT name FROM sqlite_master where type='table';");
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
		
		FileReader reader = null;
		try {
			reader = new FileReader(sqlFile);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find '" + sqlFile.getAbsolutePath() +
					"', but we just created it. This is a problem.");
			e.printStackTrace();
			System.exit(1);
		}
		char [] fileChars = new char[(int) sqlFile.length()];
		reader.read(fileChars);
		String sql = new String(fileChars);
		reader.close();
		Statement resetDatabase = returned.createStatement();
		resetDatabase.execute(sql);
		return returned;
	}

	public DAOFactory()
	{
		try {
			dbConnection = createValidConnection();
		} catch (SQLException | IOException e) {
			System.err.println("A problem occurred when creating a valid " +
					"connection to the database. Will now quit.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public IItemDAO getItemDAO() {
		return new ItemDAO(dbConnection);
	}

	@Override
	public IRootDAO getRootDAO() {
		// TODO Auto-generated method stub
		return new RootDAO(dbConnection);
	}

	@Override
	public void terminate() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
