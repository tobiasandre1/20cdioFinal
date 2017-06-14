package webapplication.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapplication.datalayer.*;
import webapplication.datalayerinterfaces.*;
import webapplication.datatransferobjects.*;

@Path("/pcservice")
public class PrescriptionCompService {

	@Context
	HttpServletRequest request;
	
	ReceptKompDAO dao = new MySQLReceptKomponentDAO();
	
	@POST
	@Path("/getpc") //view all prescription components
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptKompDTO> getData() throws DALException{
		List<ReceptKompDTO> response = dao.getReceptKompList();
		return response;
	}
	
	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response addPC(
			@FormParam("prescriptionID") int receptId,
			@FormParam("commodityID") int raavareId,
			@FormParam("nomnetto") double nomNetto,
			@FormParam("tolerance") double tolerance
			) throws DALException, URISyntaxException {

		ReceptKompDTO component = new ReceptKompDTO(receptId, raavareId, nomNetto, tolerance);
		dao.createReceptKomp(component);

		java.net.URI location = new java.net.URI("../commodity_view.html");
		return Response.temporaryRedirect(location).build();
	}
	
	@POST
	@Path("/getrecipe") //view a specific prescription components
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptKompDTO> getRecipe(IdMapper map) throws URISyntaxException, DALException {
	    return dao.getReceptKompList(map.getContent());
	}
	
}

