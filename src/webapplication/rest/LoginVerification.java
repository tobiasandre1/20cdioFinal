package rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import datalayer.UserDAO;
import dto.UserDTO;
import idatalayer.IUserDAO;
import idatalayer.IUserDAO.DALException;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginVerification {

	IUserDAO dao = new UserDAO();
	
	@POST
	@Path("/verify")
	@Consumes("application/x-www-form-urlencoded")
	public Response verifyUser(
			@FormParam("username") String userName, 
			@FormParam("password") String password
			) throws DALException, URISyntaxException{
		
		List<UserDTO> users = dao.getUserList();
		
		for(int i = 0; i < users.size(); i++){
				if(users.get(i).getUserName().equals(userName) && users.get(i).getPassword().equals(password)){
				return Response.temporaryRedirect(new java.net.URI("../userpage.html")).build();
			}
		}
	    return Response.temporaryRedirect(new java.net.URI("../")).build();
		
	}
}
