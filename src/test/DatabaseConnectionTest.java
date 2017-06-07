package test;

import static org.junit.Assert.*;

//import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import webapplication.datalayerinterfaces.DALException;
import webapplication.sqlconnector.Connector;

public class DatabaseConnectionTest {

	@Test
	public void test() {
		try { 
			new Connector(); 
			try {
				ResultSet rs = Connector.doQuery("SELECT * FROM operatoer");
				while(rs.next()){
					System.out.println(rs.getString("opr_navn"));
				}
			} catch (DALException e) {
				e.printStackTrace();
			}
		} 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		
	}

}
