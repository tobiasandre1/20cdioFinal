package webapplication.datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.ViewDAO;
import webapplication.datatransferobjects.ProduktBatchKompDTO;
import webapplication.datatransferobjects.ViewRaavareNavneDTO;
import webapplication.datatransferobjects.ViewUdskriftDTO;
import webapplication.sqlconnector.Connector;
import webapplication.sqlconnector.SQLMapper;

public class MySQLViewDAO implements ViewDAO {
	SQLMapper map = new SQLMapper();
	
	public MySQLViewDAO(){
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Override
	public List<ViewRaavareNavneDTO> getRaavareNavneList() throws DALException {
		List<ViewRaavareNavneDTO> list = new ArrayList<ViewRaavareNavneDTO>();
		ResultSet rs = Connector.doQuery(map.getStatement("view_SELECTALL_raavare_navne"));
		try
		{
			while (rs.next()) 
			{
				list.add(new ViewRaavareNavneDTO(rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getString("raavare_navn")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ViewRaavareNavneDTO> getRaavareNavneListPbId(int pbId) throws DALException {
		List<ViewRaavareNavneDTO> list = new ArrayList<ViewRaavareNavneDTO>();
		String statement = map.getStatement("view_SELECT_raavare_navne_pb");
		statement = map.insertValuesIntoString(statement, new String[]{Integer.toString(pbId)});
		
		ResultSet rs = Connector.doQuery(statement);
		try
		{
			while (rs.next()) 
			{
				list.add(new ViewRaavareNavneDTO(rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getString("raavare_navn")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ViewRaavareNavneDTO> getRaavareNavneListRbId(int rbId) throws DALException {
		List<ViewRaavareNavneDTO> list = new ArrayList<ViewRaavareNavneDTO>();
		String statement = map.getStatement("view_SELECT_raavare_navne_rb");
		statement = map.insertValuesIntoString(statement, new String[]{Integer.toString(rbId)});
		ResultSet rs = Connector.doQuery(statement);
		try
		{
			while (rs.next()) 
			{
				list.add(new ViewRaavareNavneDTO(rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getString("raavare_navn")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ViewUdskriftDTO> getUdskriftList() throws DALException {
		List<ViewUdskriftDTO> list = new ArrayList<ViewUdskriftDTO>();
		String statement = map.getStatement("view_SELECTALL_udskrift");
		ResultSet rs = Connector.doQuery(statement);
		
		try
		{
			while (rs.next()) 
			{
				ProduktBatchKompDTO pbkomp = new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
				list.add(new ViewUdskriftDTO(pbkomp, rs.getString("opr_navn"), rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getInt("receptId"), rs.getInt("status")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ViewUdskriftDTO> getUdskriftList(int pbId) throws DALException {
		List<ViewUdskriftDTO> list = new ArrayList<ViewUdskriftDTO>();
		String statement = map.getStatement("view_SELECT_udskrift");
		statement = map.insertValuesIntoString(statement, new String[]{Integer.toString(pbId)});
		ResultSet rs = Connector.doQuery(statement);
		
		try
		{
			while (rs.next()) 
			{
				ProduktBatchKompDTO pbkomp = new ProduktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
				list.add(new ViewUdskriftDTO(pbkomp, rs.getString("opr_navn"), rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getInt("recept_id"), rs.getInt("status")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

}
