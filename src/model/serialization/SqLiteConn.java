package model.serialization;

import java.sql.ResultSet;

public class SqLiteConn implements SqlConn {

	/**
	 * 
	 * @param connStr the connection string
	 */
	public SqLiteConn(String connStr) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setErrorAction(SqlErrorAction action) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultSet execSql(String qry, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] execSqlAry(String qry, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
