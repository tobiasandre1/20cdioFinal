package webapplication.sqlconnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SQLMapper {

	public static String getStatement(String str){
		
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
