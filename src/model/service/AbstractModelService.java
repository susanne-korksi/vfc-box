package model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

abstract public class AbstractModelService 
{
	private String dbPath;
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Connection connection;
	
	/**
	 * @param String dbPath
	 */
	public AbstractModelService(String dbPath) {
		if (dbPath.length() == 0) {
			throw new IllegalArgumentException("Dbpath ist anzugeben");
		}
		
		this.dbPath = dbPath;
	}
	
	/**
	 * @return Statement
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Statement getStatement() throws SQLException, ClassNotFoundException 
	{
		Connection connection = this.getConnection();
		Statement statement = connection.createStatement();
		
		return statement;
	}

	/**
	 * 
	 * @return connection
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (this.connection == null) {
			 Class.forName("org.sqlite.JDBC"); 
	         this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbPath);
			 System.out.println("inside getConn " + this.connection);
		} else if (this.connection.isClosed()) {
			Class.forName("org.sqlite.JDBC"); 
	         this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbPath);
			 System.out.println("reopen " + this.connection);
		}
		return this.connection;
	}
	
	/**
	 * @return boolean
	 */
	public boolean closeConnection() 
	{
		if (this.connection == null) {
			return true;
		}
		
		
		try {
			if (this.connection.isClosed()) {
				return true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
