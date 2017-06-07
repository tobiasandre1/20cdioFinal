package webapplication.rest;

import static org.junit.Assert.fail;

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


@Path("/userservice")
public class UserService {
	OperatoerDAO dao = new MySQLOperatoerDAO();
	
	@POST
	@Path("/getusers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OperatoerDTO> getData() throws DALException{
		List<OperatoerDTO> response = dao.getOperatoerList();
		return response;
	}
	
	@POST
	@Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteUser(
		@FormParam("submit") int id
			) throws DALException, URISyntaxException {
		OperatoerDTO opr = dao.getOperatoer(id);
		opr.setOprActive(false);
		try { dao.updateOperatoer(opr); }
		catch (DALException e) { e.printStackTrace(); }

		java.net.URI location = new java.net.URI("../userpage.html");
	    return Response.temporaryRedirect(location).build();
	}
	
	@POST
	@Path("/insert")
	@Consumes("application/x-www-form-urlencoded")
	public Response insertUser(
		@FormParam("username") String userName,
		@FormParam("password") String password,
		@FormParam("ini") String ini,
		@FormParam("role") String role,
		@FormParam("cpr") String cpr
			) throws DALException, URISyntaxException {
		
		List<String> roles = new ArrayList<String>();
		roles.add(role);
		
		OperatoerDTO user = new OperatoerDTO(0, userName, ini, password, true);
		dao.createOperatoer(user);
		
		java.net.URI location = new java.net.URI("../userpage.html");
	    return Response.temporaryRedirect(location).build();
	}
	
	@POST
	@Path("/updategetuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public OperatoerDTO updateGetUser(UserMapper map) throws URISyntaxException, DALException{
		//System.out.println(map.getContent());
	    return dao.getOperatoer(map.getContent());
	}
	
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public Response updateUser(
			@FormParam("userid") String userId,
			@FormParam("username") String userName,
			@FormParam("password") String password,
			@FormParam("ini") String ini,
			@FormParam("role") String role,
			@FormParam("oprActive") String active
			) throws URISyntaxException, DALException{
		
		List<String> roles = new ArrayList<String>();
		roles.add(role);
		
		OperatoerDTO user = new OperatoerDTO(Integer.parseInt(userId), userName, ini, password, Boolean.parseBoolean(active));
		
		dao.updateOperatoer(user);
		java.net.URI location = new java.net.URI("../userpage.html");
	    return Response.temporaryRedirect(location).build();
	}
	
	
}
