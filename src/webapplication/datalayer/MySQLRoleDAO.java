package webapplication.datalayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;

public class MySQLRoleDAO {
	SQLMapper map = new SQLMapper();
	
	public MySQLRoleDAO(){
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	public List<String> getRoleList(int oprId){
		List<String> roles = new ArrayList<String>();
		//TODO Implement
		return roles;
	}
	
	public void createRole(String role, int oprId){
		//TODO Implement
	}
	
	public void deleteRole(int oprId){
		//Deletes all roles of an operatoer
		//TODO Implement
	}
	
	public void deleteRole(String role, int oprId){
		//TODO Implement
	}

}
