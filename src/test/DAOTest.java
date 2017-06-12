package test;

import static org.junit.Assert.*;

import java.util.List;

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
	public void testRaavareDAO(){
		MySQLRaavareDAO raa = new MySQLRaavareDAO();
		
		System.out.println("Raavare nummer 3:");
		try { System.out.println(raa.getRaavare(3)); }
		catch (DALException e) { e.printStackTrace();  fail(); }
		
		System.out.println("Alle Raavarer");
		try { System.out.println(raa.getRaavareList()); }
		catch (DALException e) { e.printStackTrace();  fail(); }
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
	
	@Test
	public void testViewDAO(){
		ViewDAO dao = new MySQLViewDAO();
		
		System.out.println("Alle raavare navne");
		try { System.out.println(dao.getRaavareNavneList());}
		catch (DALException e) { e.printStackTrace();  fail();}
		
		System.out.println("Alle raavare navne med pb_id 2");
		try { 
			List<ViewRaavareNavneDTO> list = dao.getRaavareNavneListPbId(2); 
			System.out.println(list);
		}
		catch (DALException e) { e.printStackTrace();  fail();}
		
		System.out.println("Alle raavare navne med rb_id 6");
		try { System.out.println(dao.getRaavareNavneListRbId(6));}
		catch (DALException e) { e.printStackTrace();  fail();}
	}
	
	@Test
	public void testProduktBatchDAO(){
		ProduktBatchDAO dao = new MySQLProduktBatchDAO();
		
		System.out.println("Produktbatch med id 3");
		try { System.out.println(dao.getProduktBatch(5));}
		catch (DALException e) { e.printStackTrace();  fail();}
		
		ProduktBatchDTO dto = new ProduktBatchDTO(6, 0, 2);
		try {dao.createProduktBatch(dto);}
		catch (DALException e) {e.printStackTrace();}
	}
	

}
