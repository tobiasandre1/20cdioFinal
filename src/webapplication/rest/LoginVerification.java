package webapplication.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import webapplication.datalayer.*;
import webapplication.datalayerinterfaces.*;
import webapplication.datatransferobjects.*;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginVerification {
	@Context
	HttpServletRequest request;
	
	OperatoerDAO dao = new MySQLOperatoerDAO();
	
	@POST
	@Path("/verify")
	@Consumes("application/x-www-form-urlencoded")
	public Response verifyUser(
			@FormParam("username") String userName, 
			@FormParam("password") String password
			) throws DALException, URISyntaxException{
		
		
		List<OperatoerDTO> users = dao.getOperatoerList();
		
		for(OperatoerDTO opr:users){
				if(opr.getOprNavn().equals(userName) && opr.getPassword().equals(password) && opr.getOprActive()){
					request.getSession().setAttribute("user", opr); //Session attribute
					return Response.temporaryRedirect(new java.net.URI("../menu.html")).build();
				}
		}
	    return Response.temporaryRedirect(new java.net.URI("../")).build();
		
	}
	
	@GET
	@Path("/logout")
	@Consumes("application/x-www-form-urlencoded")
	public Response logout() throws URISyntaxException{
		
		request.getSession().setAttribute("user", null); //Session attribute
		
		return Response.temporaryRedirect(new java.net.URI("../")).build();
	}
	
}
