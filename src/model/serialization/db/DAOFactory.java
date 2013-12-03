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
