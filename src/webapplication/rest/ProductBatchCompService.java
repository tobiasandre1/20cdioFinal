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

@Path("/pbcservice")
public class ProductBatchCompService {

	ProduktBatchKompDAO dao = new MySQLProduktBatchKomponentDAO();

	@POST
	@Path("/getpbc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProduktBatchKompDTO> getData() throws DALException {
		List<ProduktBatchKompDTO> response = dao.getProduktBatchKompList();
		return response;
	}

	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response addPB(
		@FormParam("pbId") int pbId ,
		@FormParam("cbId") int rbId ,
		@FormParam("tara") int tara ,
		@FormParam("netto") int netto ,
		@FormParam("userId") int oprId)
					throws DALException, URISyntaxException {

		List<ProduktBatchKompDTO> productbatchcomponent = dao.getProduktBatchKompList();
		int id = 0;

		for (ProduktBatchKompDTO pbc : productbatchcomponent) {
			if (pbc.getPbId() >= id) {
				id = pbc.getPbId() + 1;
			}
		}

		ProduktBatchKompDTO pbc = new ProduktBatchKompDTO(id, rbId, tara, netto, oprId);
		dao.createProduktBatchKomp(pbc);

		java.net.URI location = new java.net.URI("../productbatchcomp_view.html");
		return Response.temporaryRedirect(location).build();
	}
	
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public Response update(
			@FormParam("pbId") int pbId ,
			@FormParam("cbId") int rbId ,
			@FormParam("tara") int tara ,
			@FormParam("netto") int netto ,
			@FormParam("userId") int oprId)
			
					throws URISyntaxException, DALException{
		
		ProduktBatchKompDTO pbc = new ProduktBatchKompDTO(pbId, rbId, tara, netto, oprId);
		
		dao.updateProduktBatchKomp(pbc);
		java.net.URI location = new java.net.URI("../productbatchcomp_view.html");
	    return Response.temporaryRedirect(location).build();
	}
}

