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

@Path("/printservice")
public class PrintService {
	
	ViewDAO dao = new MySQLViewDAO();

	@POST
	@Path("/getprint")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ViewUdskriftDTO> getPrint(IdMapper map) throws URISyntaxException, DALException{
		//System.out.println(map.getContent());
	    return dao.getUdskriftList(map.getContent());

	}

//	@POST
//	@Path("/insert")
//	@Consumes("application/x-www-form-urlencoded")
//	public Response addCommodities(
//			@FormParam("commodityname") String commodityName,
//			@FormParam("distributer") String distributer
//			) throws DALException, URISyntaxException {
//
//		List<RaavareDTO> commodities = dao.getRaavareList();
//		int id = 0;
//
//		for (RaavareDTO com : commodities) {
//			if (com.getRaavareId() >= id) {
//				id = com.getRaavareId() + 1;
//			}
//		}
//
//		RaavareDTO commodity = new RaavareDTO(id, commodityName, distributer);
//		dao.createRaavare(commodity);
//
//		java.net.URI location = new java.net.URI("../commodity_view.html");
//		return Response.temporaryRedirect(location).build();
//	}
//	
//	@POST
//	@Path("/updategetcommodity")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public RaavareDTO updateGetCommodity(IdMapper map) throws URISyntaxException, DALException{
//		//System.out.println(map.getContent());
//	    return dao.getRaavare(map.getContent());
//	}
//	
//	@POST
//	@Path("/update")
//	@Consumes("application/x-www-form-urlencoded")
//	public Response updateCommodity(
//			@FormParam("commodityID") int raavareId,
//			@FormParam("commodityname") String raavareNavn,
//			@FormParam("distributer") String leverandoer
//			) throws URISyntaxException, DALException{
//		
//		OperatoerDTO user = (OperatoerDTO) request.getSession().getAttribute("user"); //Session attribute
//		
//		java.net.URI location = new java.net.URI("../");
//		
//		for(String adminrole : user.getRoles()){
//			if(adminrole.equals("admin")){
//				RaavareDTO commodity = new RaavareDTO(raavareId, raavareNavn, leverandoer);
//				
//				dao.updateRaavare(commodity);
//				location = new java.net.URI("../commodity_view.html");
//			}
//		}
//	    return Response.temporaryRedirect(location).build();
//	}
}
