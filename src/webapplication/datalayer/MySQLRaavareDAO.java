package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.RaavareDAO;
import webapplication.datatransferobjects.RaavareDTO;
import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;

/**
 * @author Tobias
 *
 */
public class MySQLRaavareDAO implements RaavareDAO {
	SQLMapper map = new SQLMapper();
	
	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		String statement = map.getStatement("ra_SELECT");
		String[] values = new String[]{Integer.toString(raavareId)};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
	    try {
	    	if (!rs.first()) throw new DALException("Raavaren " + raavareId + " findes ikke");
	    	return new RaavareDTO (rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> list = new ArrayList<RaavareDTO>();
		ResultSet rs = Connector.doQuery(map.getStatement("ra_SELECT_ALL"));
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		String statement = map.getStatement("ra_INSERT");
		String[] values = new String[]{
				Integer.toString(raavare.getRaavareId()),
				raavare.getRaavareNavn(),
				raavare.getLeverandoer()
			};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		String statement = map.getStatement("ra_UPDATE");
		String[] values = new String[]{
				raavare.getRaavareNavn(),
				raavare.getLeverandoer(),
				Integer.toString(raavare.getRaavareId())
			};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
