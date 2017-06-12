package webapplication.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapplication.datalayer.*;
import webapplication.datalayerinterfaces.*;
import webapplication.datatransferobjects.*;

@Path("/pbservice")
public class ProductBatchService {

	ProduktBatchDAO dao = new MySQLProduktBatchDAO();

	@POST
	@Path("/getpb")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProduktBatchDTO> getData() throws DALException {
		List<ProduktBatchDTO> response = dao.getProduktBatchList();
		return response;
	}

}
