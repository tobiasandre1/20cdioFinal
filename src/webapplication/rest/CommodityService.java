package webapplication.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
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

@Path("/commodityservice")
public class CommodityService {

	RaavareDAO dao = new MySQLRaavareDAO();

	@Path("/getcommodities")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareDTO> getData() throws DALException {
		List<RaavareDTO> response = dao.getRaavareList();
		return response;
	}

	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response insertUser(
			@FormParam("commodityname") String commodityName, 
			@FormParam("distributer") String distributer
			) throws DALException, URISyntaxException {

		RaavareDTO commodity = new RaavareDTO(0, commodityName, distributer);
		dao.createRaavare(commodity);

		java.net.URI location = new java.net.URI("../commodity_view.html");
		return Response.temporaryRedirect(location).build();
	}
}
