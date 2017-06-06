package webapplication.sqlconnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SQLMapper {

	public SQLMapper(){
		String allStatements = 
				"1 = TEST: -> ? <-;\r\n" + 
				"\r\n" + 
				"opr_SELECT						= SELECT * FROM operatoer WHERE opr_id = ?;\r\n" + 
				"pb_SELECT 						= SELECT * FROM produktbatch WHERE pb_id = ?;\r\n" + 
				"pb_komponent_SELECT 			= SELECT * FROM produktbatchkomponent WHERE pb_id = ? AND rb_id = ?; \r\n" + 
				"rec_SELECT						= SELECT * FROM recept WHERE recept_id = ?;\r\n" + 
				"rec_komponent_SELECT			= SELECT * FROM receptkomponent WHERE recept_id = ? AND raavare_id = ?;\r\n" + 
				"rab_SELECT						= SELECT * FROM raavarebatch WHERE rb_id = ?;\r\n" + 
				"ra_SELECT						= SELECT * FROM raavare WHERE raavare_id = ?;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"opr_SELECT_ALL					= SELECT * FROM operatoer;\r\n" + 
				"pb_SELECT_ALL					= SELECT * FROM produktbatch;\r\n" + 
				"pb_komponent_SELECT_ALL		= SELECT * FROM produktbatchkomponent;\r\n" + 
				"pb_komponent_SELECT_ALL_rec_id	= SELECT * FROM produktbatchkomponent WHERE rb_id = ?;\r\n" + 
				"rec_SELECT_ALL					= SELECT * FROM recept;\r\n" + 
				"rec_komponent_SELECT_ALL		= SELECT * FROM receptkomponent;\r\n" + 
				"rec_komponent_SELECT_ALL_rec_id= SELECT * FROM receptkomponent WHERE recept_id = ?;\r\n" + 
				"rab_SELECT_ALL					= SELECT * FROM raavarebatch;\r\n" + 
				"rab_SELECT_ALL_raavare_id		= SELECT * FROM raavarebatch WHERE raavare_id = ?;\r\n" + 
				"ra_SELECT_ALL					= SELECT * FROM raavare;\r\n" + 
				"ro_SELECT_ALL_opr_id			= SELECT * FROM role WHERE opr_id = ?;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"opr_INSERT						= INSERT INTO operatoer(opr_id, opr_navn, ini, password) VALUES (?, '?', '?', '?');\r\n" + 
				"pb_INSERT						= INSERT INTO produktbatch(pb_id, status, recept_id) VALUES (?, '?', ?);\r\n" + 
				"pb_komponent_INSERT			= INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) VALUES (?, ?, ?, ?, ?);\r\n" + 
				"rec_INSERT						= INSERT INTO recept(recept_id, recept_navn) VALUES (?, '?');\r\n" + 
				"rec_komponent_INSERT			= INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES (?, ?, ?, ?);\r\n" + 
				"rab_INSERT						= INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES (?, ?, ?);\r\n" + 
				"ra_INSERT						= INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES (?, '?', '?');\r\n" + 
				"ro_INSERT						= INSERT INTO role(opr_id, role) VALUES (?, '?');\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"opr_UPDATE						= UPDATE operatoer SET opr_navn = '?', ini = '?', password = '?', opr_active = '?' WHERE opr_id = ?;\r\n" + 
				"pb_UPDATE						= UPDATE produktbatch SET status = ?, recept_id = ? WHERE pb_id = ?;\r\n" + 
				"pb_komponent_UPDATE			= UPDATE produktbatchkomponent SET tara = ?, netto = ?, opr_id = ? WHERE pb_id = ? AND rb_id = ?;\r\n" + 
				"rec_UPDATE						= UPDATE recept SET recept_navn = '?' WHERE recept_id = ?;\r\n" + 
				"rec_komponent_UPDATE			= UPDATE receptkomponent SET nom_netto = ?, tolerance = ? WHERE recept_id = ? AND raavare_id = ?;\r\n" + 
				"rab_UPDATE						= UPDATE raavarebatch SET raavare_id = ?, maengde = ? WHERE rb_id = ?;\r\n" + 
				"ra_UPDATE						= UPDATE raavare SET raavare_navn = '?', leverandoer = '?' WHERE raavare_id = ?;\r\n" + 
				"\r\n" + 
				"ro_delete						= DELETE FROM role WHERE opr_id = ?;\r\n" + 
				"\r\n" + 
				"view_create_mad				= CREATE VIEW mad AS SELECT rk.recept_id, rk.raavare_id, re.recept_navn, ra.raavare_navn, ra.leverandoer FROM receptkomponent rk NATURAL JOIN recept re NATURAL JOIN raavare ra;\r\n" + 
				"view_create_vejning			= CREATE VIEW vejning AS SELECT opr.opr_id, opr.opr_navn, pbk.tara, pbk.netto, ra.raavare_id, ra.raavare_navn, rab.maengde FROM operatoer opr NATURAL JOIN produktbatchkomponent pbk NATURAL JOIN raavare ra NATURAL JOIN raavarebatch rab;\r\n" + 
				"view_SELECT_mad				= SELECT * FROM mad WHERE recept_id = ? AND raavare_id = ?;\r\n" + 
				"view_SELECT_vejning			= SELECT * FROM vejning WHERE opr_id = ?;\r\n" + 
				"view_drop_mad					= DROP VIEW mad;\r\n" + 
				"view_drop_vejning				= DROP VIEW vejning; \r\n" + 
				"view_SELECTALL_mad				= SELECT * FROM mad;\r\n" + 
				"view_SELECTALL_vejning			= SELECT * FROM vejning;\r\n" + 
				"\r\n" + 
				"opr_specific_name				= SELECT opr_navn FROM operatoer WHERE orp_id = ?;\r\n"
				;
		allStatements.replace("\r\n", "");
		String[] statementsArray = allStatements.split(";");
		for(String i : statementsArray){
			System.out.println(i);
		}
				
	}
	
	public String getStatement(String str){
		
		Properties props = new Properties();
		try {
			File file = new File("SQL.txt");
			FileInputStream in = new FileInputStream(file);
			props.load(in);
			String res = props.getProperty(str);
			in.close();
			return res; 
		} catch (FileNotFoundException e){
			throw new IllegalStateException("SQL file does not exist");
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load properties");
		}
	}
	
	public static String insertValuesIntoString(String statement, String[] values){
		for(int i=0; i<values.length; i++){
			statement = statement.replaceFirst("[?]", values[i]);
		}
		return statement;
	}
	
	private class StringPair{
		String key;
		String value;
		
		StringPair(String key, String value){
			this.key = key;
			this.value = value;
		}
	}
	
}
