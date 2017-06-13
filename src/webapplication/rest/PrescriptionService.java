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

@Path("/prescriptionservice")
public class PrescriptionService {
ReceptDAO dao = new MySQLReceptDAO();
	
	@POST
	@Path("/getprescriptions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptDTO> getData() throws DALException{
		List<ReceptDTO> response = dao.getReceptList();
		return response;
	}
	
	
	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response insertPrescription(
		@FormParam("prescriptname") String prescriptName) throws DALException, URISyntaxException {
		
		List<ReceptDTO> recepter = dao.getReceptList();
		int id = 0;
		for(ReceptDTO opr:recepter){
			if(opr.getReceptId()>=id){id=opr.getReceptId()+1;}
		}
		
		ReceptDTO recept = new ReceptDTO(id, prescriptName);
		dao.createRecept(recept);
		
		java.net.URI location = new java.net.URI("../prescription_view.html");
	    return Response.temporaryRedirect(location).build();
	}
}
