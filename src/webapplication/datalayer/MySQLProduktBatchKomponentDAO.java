package webapplication.datalayer;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.ProduktBatchKompDAO;
import webapplication.datatransferobjects.ProduktBatchKompDTO;
import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;

/**
 * @author Gustav
 *
 */
public class MySQLProduktBatchKomponentDAO implements ProduktBatchKompDAO {
	SQLMapper map = new SQLMapper();
	
	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {

		String statement = map.getStatement("pb_komponent_SELECT");
		String[] values = new String[]{Integer.toString(pbId), Integer.toString(rbId)};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println("Query: "+statement);
		ResultSet rs = Connector.doQuery(statement);
		
		try {
	    	if (!rs.first()) throw new DALException("Produkt batch komponent med pbId = " + pbId +" Eller rbId = " + rbId + " findes ikke");
	    	
	    	return new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
	    	
		}   
		catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		String statement = map.getStatement("pb_komponent_SELECT_ALL_rec_id");
		statement = map.insertValuesIntoString(statement, new String[]{Integer.toString(pbId)});
		ResultSet rs = Connector.doQuery(statement);
		
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
		
	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		String statement = map.getStatement("pb_komponent_SELECT_ALL");
		ResultSet rs = Connector.doQuery(statement);
		
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}


	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		
		String statement = map.getStatement("pb_komponent_INSERT");
		String[] values = new String[]{
									Integer.toString(produktbatchkomponent.getPbId()), 
									Integer.toString(produktbatchkomponent.getRbId()),
									Double.toString(produktbatchkomponent.getTara()),
									Double.toString(produktbatchkomponent.getNetto()),
									Integer.toString(produktbatchkomponent.getOprId())
						};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);
		
	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
	
		String statement = map.getStatement("pb_komponent_UPDATE");
		String[] values = new String[]{
									Double.toString(produktbatchkomponent.getTara()),
									Double.toString(produktbatchkomponent.getNetto()),
									Integer.toString(produktbatchkomponent.getOprId()),
									Integer.toString(produktbatchkomponent.getPbId()), 
									Integer.toString(produktbatchkomponent.getRbId())
						};
		statement = map.insertValuesIntoString(statement, values);
		System.out.println(statement);
		Connector.doUpdate(statement);

	}

}
