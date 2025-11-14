package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.zkoss.zhtml.Messagebox;

public class DBConnection {
	 private static final String DB_NAME = "banking_system"; 
	 private static String url = "jdbc:mysql://localhost:3306/"+DB_NAME;
	 private static String username = "root";
	 private static String password = "root123";
	 private static String driver="com.mysql.cj.jdbc.Driver";
	    
	    
	private static Connection con=null;
	private DBConnection() {
		
	}
	
	public static boolean createDb() {
		String query = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
		try {
			Statement st = DBConnection.getMyConnection().createStatement();
			int row = st.executeUpdate(query);
			if(row > 0) {
				System.out.println("database created");
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static Connection getMyConnection() {
		try {
			if(con==null || con.isClosed()) {
				Class.forName(driver);
				con = DriverManager.getConnection(url, username, password);
				//Messagebox.show("CONNECTION successfully done");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Messagebox.show(e.getMessage());
		}
		return con;
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnection.getMyConnection());
	}
}
