package webapplication.datalayerinterfaces;

import java.util.List;

import webapplication.datatransferobjects.ViewRaavareNavneDTO;

public interface ViewDAO{

	public List<ViewRaavareNavneDTO> getRaavareNavneList() throws DALException;
	public List<ViewRaavareNavneDTO> getRaavareNavneListPbId(int pbId) throws DALException;
	public List<ViewRaavareNavneDTO> getRaavareNavneListRbId(int rbId) throws DALException;
}
