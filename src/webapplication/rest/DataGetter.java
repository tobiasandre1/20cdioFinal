package webapplication.rest;

import java.util.ArrayList;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import webapplication.datalayer.*;
import webapplication.datalayerinterfaces.*;
import webapplication.datatransferobjects.*;


@Path("datagetter")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataGetter {
	OperatoerDAO dao = new MySQLOperatoerDAO();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<OperatoerDTO> getData() throws DALException{
		List<OperatoerDTO> response = dao.getOperatoerList();
		return response;
	}
}
