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

@Path("/cbservice")
public class CommodityBatchService {

	RaavareBatchDAO dao = new MySQLRaavareBatchDAO();

	@POST
	@Path("/getcb")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareBatchDTO> getData() throws DALException {
		List<RaavareBatchDTO> response = dao.getRaavareBatchList();
		return response;
	}

	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response add(@FormParam("commodityID") int raavareId, @FormParam("amount") int maengde)
			throws DALException, URISyntaxException {

		List<RaavareBatchDTO> commoditybatch = dao.getRaavareBatchList();
		int rbId = 0;

		for (RaavareBatchDTO cb : commoditybatch) {
			if (cb.getRbId() >= rbId) {
				rbId = cb.getRbId() + 1;
			}
		}

		RaavareBatchDTO cb = new RaavareBatchDTO(rbId, raavareId, maengde);
		dao.createRaavareBatch(cb);

		java.net.URI location = new java.net.URI("../commoditybatch_view.html");
		return Response.temporaryRedirect(location).build();
	}

}
