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
	SQLMapper map = new SQLMapper();
	
	public MySQLOperatoerDAO(){
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		//MySQLRoleDAO roledao = new MySQLRoleDAO();
	}
	
	@Override
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		
		String[] values = new String[]{Integer.toString(oprId)};
		
		String statement = map.getStatement("opr_SELECT");
		statement = map.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
		
		OperatoerDTO temp;
		
	    try {
	    	//Get the operatoer
	    	if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke");
	    	temp = new OperatoerDTO (rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("password"), rs.getBoolean("opr_active"));
	    	//TODO manage roles
	    	return temp;
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}

	@Override
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		
		
		
		OperatoerDTO temp;
		try
		{
			ResultSet rs = Connector.doQuery(map.getStatement("opr_SELECT_ALL"));
			
			while (rs.next()) 
			{
				temp = new OperatoerDTO(rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("password"), rs.getBoolean("opr_active"));		
				list.add(temp);
			}
			
			rs = Connector.doQuery(map.getStatement("ro_SELECT_ALL"));
			
			/*WORKS*/
			List<RolePair> roles = new ArrayList<RolePair>();
			while (rs.next()){
				roles.add( new RolePair(rs.getInt("opr_id"), rs.getString("role")));
			}
			/*END OF WORKS*/
		
			for(OperatoerDTO opr : list){
				for(RolePair role: roles){
					if(role.getOprId() == opr.getOprId())
						opr.addRole(role.getRole());
				}
			}
			
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createOperatoer(OperatoerDTO opr) throws DALException {
		String statement = map.getStatement("opr_INSERT");
		String[] values = new String[]{Integer.toString(opr.getOprId()), opr.getOprNavn(), opr.getIni(), opr.getPassword(), String.valueOf(opr.getOprActive())};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		//Update operatoer table
		String statement = map.getStatement("opr_UPDATE");
		String[] values = new String[]{opr.getOprNavn(), opr.getIni(), opr.getPassword(), String.valueOf(opr.getOprActive()), Integer.toString(opr.getOprId())}; //Only difference from INSERT is operatoer id is at the end
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}
	
	private class RolePair{
		private int oprId;
		private String role;
		
		RolePair(int oprId, String role){
			this.oprId = oprId;
			this.role = role;
		}
		
		public int getOprId (){return oprId;}
		public String getRole(){return role;}
	}

}
