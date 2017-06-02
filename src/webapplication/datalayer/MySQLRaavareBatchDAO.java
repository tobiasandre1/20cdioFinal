package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.RaavareBatchDAO;
import webapplication.datatransferobjects.RaavareBatchDTO;


/**
 * @author Danny
 *
 */
public class MySQLRaavareBatchDAO implements RaavareBatchDAO {

	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		String statement = SQLMapper.getStatement("rab_SELECT");
		String[] values = new String[]{Integer.toString(rbId)};
		statement = SQLMapper.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
		
	    try {
	    	if (!rs.first()) throw new DALException("Raavarebatch " + rbId + " findes ikke");
	    	return new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery(SQLMapper.getStatement("rab_SELECT_ALL")); //Without value inserted. Returns all rows
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		String statement = SQLMapper.getStatement("rab_SELECT_ALL_raavare_id");
		statement = SQLMapper.insertValuesIntoString(statement, new String[]{Integer.toString(raavareId)});
		ResultSet rs = Connector.doQuery(statement);
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		String statement = SQLMapper.getStatement("rab_INSERT");
		String[] values = new String[]{
				Integer.toString(raavarebatch.getRbId()), 
				Integer.toString(raavarebatch.getRaavareId()), 
				Double.toString(raavarebatch.getMaengde())
			};
		statement = SQLMapper.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		String statement = SQLMapper.getStatement("rab_UPDATE");
		String[] values = new String[]{ 
				Integer.toString(raavarebatch.getRaavareId()), 
				Double.toString(raavarebatch.getMaengde()),
				Integer.toString(raavarebatch.getRbId())
			};
		statement = SQLMapper.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
