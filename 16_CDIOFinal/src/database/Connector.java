package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector
{
	/**
	 * To connect to a MySQL-server
	 * 
	 * @param url must have the form
	 * "jdbc:mysql://<server>/<database>" for default port (3306)
	 * OR
	 * "jdbc:mysql://<server>:<port>/<database>" for specific port
	 * more formally "jdbc:subprotocol:subname"
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	
	private Connection connection;
	private Statement statement;
	
	//# New
	
	public Connector(String server, int port, String database, String username, String password) throws DALException {
		
		try {
			
			connection = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, username, password);
			statement = connection.createStatement();
			
		} catch (SQLException e) {
			
			try {
				connection.close();
			} catch (SQLException ex) {
			}
			
			throw new DALException(e);
			
		}
		
	}
	
	//# Close
	
	public void Close() {

		try {
			connection.close();
		} catch (SQLException ex) {
		}
		try {
			statement.close();
		} catch (SQLException ex) {
		}
		
	}
	
	//# Functions
	
	public ResultSet doQuery(String command) throws DALException
	{
		
		try {
			return statement.executeQuery(command);
		} catch (SQLException e) {
			throw new DALException(e);
		}
		
	}
	public int doUpdate(String command) throws DALException
	{
		
		try {
			return statement.executeUpdate(command);
		} catch (SQLException e) {
			throw new DALException(e);
		}
		
	}
	
	
}