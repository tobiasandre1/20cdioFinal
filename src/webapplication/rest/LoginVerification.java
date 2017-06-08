package webapplication.rest;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import webapplication.sqlconnector.Connector;
import webapplication.datalayer.*;
import webapplication.datalayerinterfaces.*;
import webapplication.datatransferobjects.*;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginVerification {

	OperatoerDAO dao = new MySQLOperatoerDAO();
	
	@POST
	@Path("/verify")
	@Consumes("application/x-www-form-urlencoded")
	public Response verifyUser(
			@FormParam("username") String userName, 
			@FormParam("password") String password
			) throws DALException, URISyntaxException{
		
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		List<OperatoerDTO> users = dao.getOperatoerList();
		
		for(int i = 0; i < users.size(); i++){
				if(users.get(i).getOprNavn().equals(userName) && users.get(i).getPassword().equals(password)){
				return Response.temporaryRedirect(new java.net.URI("../user_view.html")).build();
			}
		}
	    return Response.temporaryRedirect(new java.net.URI("../")).build();
		
	}
}
