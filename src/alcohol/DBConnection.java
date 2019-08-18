package alcohol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {

	Connection c =null;
	Statement stm = null;
	
	
	DBConnection(){
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:base.db");
			
			
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			
		}
		
	}
	
	public void listDrinks() {
		try {
			this.stm=c.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM DRINK");
			while(rs.next()) {
				int id= rs.getInt("id");
				String name = rs.getString("name");
				String desc = rs.getString("desc");
				System.out.println("ID: "+id+" Name: " + name + "Description: " + desc);
				
			}
			
		}catch(Exception e){
			
			System.out.println("Error: "+e.getMessage());
		}
		
	}
	
	public void closeDB() {
		try{
			c.close();
		}catch(Exception e) {
			//error
		}
	}
	
	public void executeQuery(String query) {
		try {
			this.stm=c.createStatement();
			stm.executeQuery(query);
		}catch(Exception e){
			System.out.println("Error: "+ e.getMessage());
		}
		
	}
	
	public ResultSet selectQuery(String query) {
		try {
			this.stm=c.createStatement();
			ResultSet rs = stm.executeQuery(query);
			return rs;
			
		}catch(Exception e){
			System.out.println("Error: "+ e.getMessage());
			return null;
		}
		
	}
	
	
}
