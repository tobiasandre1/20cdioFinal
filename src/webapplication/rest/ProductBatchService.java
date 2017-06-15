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

	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response add(
			@FormParam("prescriptionID") int receptId,
			@FormParam("status") int status) 
					throws DALException, URISyntaxException {

		List<ProduktBatchDTO> productbatch = dao.getProduktBatchList();
		int id = 0;

		for (ProduktBatchDTO pb : productbatch) {
			if (pb.getPbId() >= id) {
				id = pb.getPbId() + 1;
			}
		}

		ProduktBatchDTO pb = new ProduktBatchDTO(id, status, receptId);
		dao.createProduktBatch(pb);

		java.net.URI location = new java.net.URI("../productbatch_view.html");
		return Response.temporaryRedirect(location).build();
	}
	/*
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public Response update(
			@FormParam("pbId") int pbId,
			@FormParam("prescriptionID") int receptId,
			@FormParam("status") int status)
			
					throws URISyntaxException, DALException{
		
		ProduktBatchDTO pb = new ProduktBatchDTO(pbId, status, receptId);
		
		dao.updateProduktBatch(pb);
		java.net.URI location = new java.net.URI("../productbatch_view.html");
	    return Response.temporaryRedirect(location).build();
	} */
}

