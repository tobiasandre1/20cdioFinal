package test;

import static org.junit.Assert.*;

import webapplication.datatransferobjects.OperatoerDTO;
import webapplication.sqlconnector.*;

import org.junit.Test;

public class SQLMapperTest {

	@Test
	public void statement() {
		SQLMapper map = new SQLMapper();
		
		String statement = map.getStatement("opr_SELECT");
		assertEquals(statement, "SELECT * FROM operatoer WHERE opr_id = ?;");
		
		statement = map.getStatement("ra_INSERT");
		assertEquals(statement, "INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES (?, '?', '?');");
		
		statement = map.getStatement("opr_INSERT");
		assertEquals(statement, "INSERT INTO operatoer(opr_id, opr_navn, ini, password, opr_active) VALUES (?, '?', '?', '?', '?');");
		
	}
	
	@Test
	public void valuesIntoString(){
		SQLMapper map = new SQLMapper();
		String statement = map.getStatement("opr_INSERT");
		OperatoerDTO opr = new OperatoerDTO(0, "bla", "blu", "bli", false); 
		
		String[] values = new String[]{Integer.toString(opr.getOprId()), opr.getOprNavn(), opr.getIni(), opr.getPassword(), String.valueOf(opr.getOprActive())};
		statement = map.insertValuesIntoString(statement, values);
		assertEquals(statement, "INSERT INTO operatoer(opr_id, opr_navn, ini, password, opr_active) VALUES (0, 'bla', 'blu', 'bli', '"+String.valueOf(false)+"');");
		
	}

}
