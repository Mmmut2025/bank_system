package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.zkoss.zhtml.Messagebox;

import models.Nominee;
import utils.DBConnection;

public class NomineeDao {
	public NomineeDao() {
		createNomineeSchema();
	}
	
	public static void createNomineeSchema() {
		String q = """
					CREATE TABLE IF NOT EXISTS nominee (
				    nominee_id BIGINT PRIMARY KEY,
				    nominee_name VARCHAR(100) NOT NULL,
				    nominee_relation VARCHAR(50) NOT NULL
				);
				""";
		
		try {
			Statement statement = DBConnection.getMyConnection().createStatement();
			statement.executeUpdate(q);
			System.out.println("customer table created");
		} catch (SQLException e) {
			e.printStackTrace();
			Messagebox.show(e.getMessage());
		}
	}
	
	
	public boolean createNominee(Nominee nominee) {
		String q = "INSERT INTO nominee VALUES ( ?, ?, ?);";
		try(PreparedStatement statement = DBConnection.getMyConnection().prepareStatement(q)){
			
			statement.setLong(1, nominee.getNominee_id());
			statement.setString(2, nominee.getNominee_name());
			statement.setString(3, nominee.getNominee_relation());

			if(statement.executeUpdate()>0) {
				System.out.println("nominee created successfully");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Messagebox.show(e.getMessage());
		}
		return false;
	}
	
	public boolean isNomineeExist(long nominee_id) {
		String q = "select * from nominee where nominee_id = ?";
		try(PreparedStatement statement = DBConnection.getMyConnection().prepareStatement(q)){
			
			statement.setLong(1, nominee_id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				//Messagebox.show("Nominee already exists in db");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Messagebox.show(e.getMessage());
		}
		return false; 
	}
}
