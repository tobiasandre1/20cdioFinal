package webapplication.datalayerinterfaces;

import java.util.List;

import webapplication.datatransferobjects.ViewRaavareNavneDTO;
import webapplication.datatransferobjects.ViewUdskriftDTO;

public interface ViewDAO{

	public List<ViewRaavareNavneDTO> getRaavareNavneList() throws DALException;
	public List<ViewRaavareNavneDTO> getRaavareNavneListPbId(int pbId) throws DALException;
	public List<ViewRaavareNavneDTO> getRaavareNavneListRbId(int rbId) throws DALException;
	
	public List<ViewUdskriftDTO> getUdskriftList() throws DALException;
	public List<ViewUdskriftDTO> getUdskriftList(int pbId) throws DALException;
}
