package model.serialization;

import org.sqlite.JDBC;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SqLiteConn implements SqlConn {
	private Connection conn = null;
	private String connStr;
	private SqlErrorAction action;
	/**
	 * 
	 * @param connStr the connection string
	 */
	public SqLiteConn(String connStr) {
		this.connStr = connStr;
		action = SqlErrorAction.NONE;
	}

	@Override
	public void open() {
		if (conn != null) {
			try
			{
			conn = DriverManager.getConnection(connStr);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setErrorAction(SqlErrorAction action) {
		this.action = action;
	}

	@Override
	public ResultSet execSql(String qry, int timeout) {
		try
		{
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(timeout);
			ResultSet rs = stmt.executeQuery(qry);
			return rs;
		}
		catch (SQLException e) {
			switch(action) {
			case THROW:
				throw new SqlException(e);
			case LOG:
				break;
			case GUI:
				JOptionPane.showMessageDialog(null, e.getMessage());
				break;
			case NONE:
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public String[][] execSqlAry(String qry, int timeout) {
		ResultSet rs = execSql(qry, timeout);
		
		if (rs != null) {
			try {
				List<String[]> ary = new ArrayList<String[]>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				while (!rs.isAfterLast()) {
					String[] rowData = new String[colCount];
					for (int i = 1; i <= colCount; i++) {
						rowData[i - 1] = rs.getString(i);
					}
					ary.add(rowData);
					rs.next();
				}
				if (ary.size() > 0) {
					String[][] retVal = (String[][]) ary.toArray();
					return retVal;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return null;
	}

}
