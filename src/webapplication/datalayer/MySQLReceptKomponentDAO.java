package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.ReceptKompDAO;
import webapplication.datatransferobjects.ReceptKompDTO;

/**
 * @author Theis
 *
 */
public class MySQLReceptKomponentDAO implements ReceptKompDAO {
	SQLMapper map = new SQLMapper();
	
	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		String statement = map.getStatement("rec_komponent_SELECT");
		String[] values = new String[]{Integer.toString(receptId), Integer.toString(raavareId)};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
		
	    try {
	    	if (!rs.first()) throw new DALException("Receptkomponenten for recept " + receptId + " og raavare " + raavareId + " findes ikke");
	    	return new ReceptKompDTO (rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		String statement = map.getStatement("rec_komponent_SELECT_ALL_rec_id");
		statement = map.insertValuesIntoString(statement, new String[]{Integer.toString(receptId)});
		ResultSet rs = Connector.doQuery(statement);
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery(map.getStatement("rec_komponent_SELECT_ALL"));
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		String statement = map.getStatement("rec_komponent_INSERT");
		String[] values = new String[]{
				Integer.toString(receptkomponent.getReceptId()), 
				Integer.toString(receptkomponent.getRaavareId()), 
				Double.toString(receptkomponent.getNomNetto()), 
				Double.toString(receptkomponent.getTolerance())
			};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		String statement = map.getStatement("rec_komponent_UPDATE");
		String[] values = new String[]{
				Double.toString(receptkomponent.getNomNetto()), 
				Double.toString(receptkomponent.getTolerance()),
				Integer.toString(receptkomponent.getReceptId()), 
				Integer.toString(receptkomponent.getRaavareId())
			};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
