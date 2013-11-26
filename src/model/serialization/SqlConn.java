package model.serialization;

import java.sql.ResultSet;

/**
 * The general interface for the SqlConn wrapper.
 * Constructor should accept a connection string.
 * 
 * @author nstandif
 *
 */
public interface SqlConn {
	/**
	 * Opens an SQL connection, if it isn't already open.
	 * 
	 * {@pre none}
	 * 
	 * {@post closed if not open}
	 */
	
	public void open();
	/**
	 * forces an SQL connection to close, if it is open.
	 * 
	 * {@pre none}
	 * 
	 * {@post connection closed if open}
	 */
	
	public void close();
	/**
	 * Tells SqlConn what to do if an error occurs
	 * 
	 * @param action the action to perform in case of an error
	 * 
	 * {@pre action != null}
	 * 
	 * {@post action = value}
	 */
	public void setErrorAction(SqlErrorAction action);
	
	
	/**
	 * Executes sql statement, prepares, and returns result.
	 * 
	 * @param qry the query to execute
	 * 
	 * @param timeout the timeout in ms. O if no timeout.
	 * 
	 * @return a java.sql.ResultSet
	 *
	 * {@pre qry != null && timeout >= 0}
	 * 
	 * {@post result set as a java.sql.ResultSet}
	 */
	public ResultSet execSql(String qry, int timeout);
	
	/**
	 * Executes sql statement, prepares, and returns result as a String array.
	 * 
	 * @param qry the query to execute 
	 * 
	 * @param timeout the timeout in milliseconds
	 * 
	 * @return a 2d array of strings
	 * 
	 * {@pre qry != null && timeout >= 0}
	 * 
	 * {@post result set as an array}
	 */
	public String[][] execSqlAry(String qry, int timeout);
}
