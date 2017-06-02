package rest;

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
import javax.ws.rs.ext.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import datalayer.UserDAO;
import dto.UserDTO;
import idatalayer.IUserDAO;
import idatalayer.IUserDAO.DALException;

@Path("/userservice")
public class UserService {
	IUserDAO dao = new UserDAO();
	
	@POST
	@Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteUser(
		@FormParam("submit") int id
			) throws DALException, URISyntaxException {
		
		dao.deleteUser(id);
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
		
		UserDTO user = new UserDTO(0, userName, ini, roles, password, cpr);
		dao.createUser(user);
		
		java.net.URI location = new java.net.URI("../userpage.html");
	    return Response.temporaryRedirect(location).build();
	}
	
	@POST
	@Path("/updategetuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO updateGetUser(UserMapper map) throws URISyntaxException, DALException{
		//System.out.println(map.getContent());
	    return dao.getUser(map.getContent());
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
			@FormParam("cpr") String cpr
			) throws URISyntaxException, DALException{
		
		List<String> roles = new ArrayList<String>();
		roles.add(role);
		
		UserDTO user = new UserDTO(Integer.parseInt(userId), userName, ini, roles, password, cpr);
		
		dao.updateUser(user);
		java.net.URI location = new java.net.URI("../userpage.html");
	    return Response.temporaryRedirect(location).build();
	}
	
	
}
