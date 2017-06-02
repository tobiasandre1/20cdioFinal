package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.sqlconnector.Connector;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.OperatoerDAO;
import webapplication.datatransferobjects.OperatoerDTO;
import webapplication.sqlconnector.SQLMapper;

public class MySQLOperatoerDAO implements OperatoerDAO {

	@Override
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		
		String[] values = new String[]{Integer.toString(oprId)};
		
		String rolestatement = SQLMapper.getStatement("ro_SELECT_opr_id");
		String statement = SQLMapper.getStatement("opr_SELECT");
		
		rolestatement = SQLMapper.insertValuesIntoString(rolestatement, values);
		statement = SQLMapper.insertValuesIntoString(statement, values);
		
		System.out.println("Query: "+rolestatement);
		System.out.println("Query: "+statement);
		
		ResultSet role_rs = Connector.doQuery(rolestatement);
		ResultSet rs = Connector.doQuery(statement);
		
		OperatoerDTO temp;
		
	    try {
	    	//Get the operatoer
	    	if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke");
	    	temp = new OperatoerDTO (rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("password"));
	    	
	    	//Get the roles
	    	try{
    			List<String> roles = new ArrayList<String>();
    			while (role_rs.next()){
    				roles.add(role_rs.getString("role"));
    			}
    			temp.setRoles(roles);
    			return temp;
    		}
    		catch(SQLException e){System.out.println("Error fetching roles" + e);throw new DALException(e);}
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}

	@Override
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		
		String rolestatement; 
		String[] values; 
		
		ResultSet rs = Connector.doQuery(SQLMapper.getStatement("opr_SELECT_ALL"));
		ResultSet role_rs;
		OperatoerDTO temp;
		try
		{
			while (rs.next()) 
			{
				temp = new OperatoerDTO(rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("password"));
				
			
		    	try{
		    		//Preparing role fetch statement
		    		rolestatement = SQLMapper.getStatement("ro_SELECT_opr_id");
		    		values = new String[]{Integer.toString(temp.getOprId())};
		    		rolestatement = SQLMapper.insertValuesIntoString(rolestatement, values);
		    		role_rs = Connector.doQuery("ro_SELECT_opr_id");
		    		
		    		//Fetching roles
		    		List<String> roles = new ArrayList<String>();
		    		while (role_rs.next()){
		    			roles.add(role_rs.getString("role"));
		    		}
		    			
		    		temp.setRoles(roles);
		     	}
		    	catch(SQLException e){System.out.println("Error fetching roles" + e);throw new DALException(e);}
				
				list.add(temp);
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createOperatoer(OperatoerDTO opr) throws DALException {
		String statement = SQLMapper.getStatement("opr_INSERT");
		String[] values = new String[]{Integer.toString(opr.getOprId()), opr.getOprNavn(), opr.getIni(), opr.getPassword()};
		statement = SQLMapper.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		String statement = SQLMapper.getStatement("opr_UPDATE");
		String[] values = new String[]{opr.getOprNavn(), opr.getIni(), opr.getPassword(), Integer.toString(opr.getOprId())}; //Only difference from INSERT is operatoer id is at the end
		statement = SQLMapper.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
