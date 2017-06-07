package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.ReceptDAO;
import webapplication.datatransferobjects.ReceptDTO;

/**
 * @author Frederik
 *
 */
public class MySQLReceptDAO implements ReceptDAO {
	SQLMapper map = new SQLMapper();
	
	public MySQLReceptDAO(){
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		String statement = map.getStatement("rec_SELECT");
		String[] values = new String[]{Integer.toString(receptId)};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
		
	    try {
	    	if (!rs.first()) throw new DALException("Recepten " + receptId + " findes ikke");
	    	return new ReceptDTO (rs.getInt("recept_id"), rs.getString("recept_navn"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		List<ReceptDTO> list = new ArrayList<ReceptDTO>();
		ResultSet rs = Connector.doQuery(map.getStatement("rec_SELECT_ALL"));
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptDTO (rs.getInt("recept_id"), rs.getString("recept_navn")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRecept(ReceptDTO recept) throws DALException {
		String statement = map.getStatement("rec_INSERT");
		String[] values = new String[]{
								Integer.toString(recept.getReceptId()), 
								recept.getReceptNavn()
				};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateRecept(ReceptDTO recept) throws DALException {
		String statement = map.getStatement("rec_UPDATE");
		String[] values = new String[]{
						recept.getReceptNavn(),	
						Integer.toString(recept.getReceptId())
								
				};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
