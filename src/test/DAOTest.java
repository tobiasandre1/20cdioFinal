package test;

import static org.junit.Assert.*;

//import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import webapplication.datatransferobjects.*;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.*;
import webapplication.datalayer.*;

import webapplication.sqlconnector.Connector;

public class DAOTest {

	@Test
	public void testConnection() {
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
	
	@Test
	public void testOperatoerDAO(){
		OperatoerDAO opr = new MySQLOperatoerDAO();
		
		try { System.out.println(opr.getOperatoer(1)); }
		catch (DALException e) { e.printStackTrace();  fail();}
		
		System.out.println("Alle operatoerer");
		try { System.out.println(opr.getOperatoerList());}
		catch (DALException e) { e.printStackTrace();  fail();}
		
		System.out.println("Indsaettelse af ny operatoer med opr_id =  5");
		OperatoerDTO oprDTO = new OperatoerDTO(5,"Don Juan","DJ","iloveyou", true);
		oprDTO.addRole("foreman");
		try { opr.createOperatoer(oprDTO); }
		catch (DALException e) { e.printStackTrace(); /*fail();*/}
		
		System.out.println("Operatoer nummer 5:");
		try { System.out.println(opr.getOperatoer(5)); }
		catch (DALException e) { e.printStackTrace(); fail();}
		
		System.out.println("Opdatering af initialer for operatoer nummer 5");
		oprDTO.setIni("DoJu");
		try { opr.updateOperatoer(oprDTO); }
		catch (DALException e) { e.printStackTrace(); fail(); }
		
		System.out.println("Set operatoer 5 til at være inaktiv");
		oprDTO.setOprActive(false);
		try { opr.updateOperatoer(oprDTO); }
		catch (DALException e) { e.printStackTrace(); fail(); }
		
		System.out.println("Giv operatoer 5 ny rolle");
		oprDTO.addRole("pharmaceut");
		try { opr.updateOperatoer(oprDTO); }
		catch (DALException e) { e.printStackTrace(); fail(); }
		
	}
	

}
