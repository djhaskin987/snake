package model.serialization.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory implements IDAOFactory {
	private Connection dbConnection;
	
	public DAOFactory()
	{
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String url = "jdbc:sqlite:./inventory-tracker.sqlite";
		try {
			dbConnection = DriverManager.getConnection(url);
		} catch (SQLException e) {
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
