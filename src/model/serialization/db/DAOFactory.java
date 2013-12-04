package model.serialization.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.nio.file.Files;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DAOFactory implements IDAOFactory, Closeable {
	private JDBCWrapper dbConnection;

	public DAOFactory()
	{
		dbConnection = new JDBCWrapper();
	}
	public DAOFactory(String databaseFileName)
	{
		dbConnection = new JDBCWrapper(databaseFileName);
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
	public IProductDAO getProductDAO() {
		return new ProductDAO(dbConnection);
	}
	@Override
	public IProductContainerDAO getProductContainerDAO() {
		return new ProductContainerDAO(dbConnection);
	}

	@Override
	public void close() throws IOException {
		dbConnection.close();
	}
}
