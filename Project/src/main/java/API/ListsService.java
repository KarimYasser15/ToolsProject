package API;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import Database.ListsDao;
import EJBs.Board;
import EJBs.Lists;

@Stateless
@Path("/list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListsService {

	@EJB
	private ListsDao listDao;
	
	@POST
	@Path("/createList")
	public String createList(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName)
	{
		return listDao.createList(boardName, listName);
	}
	
	@GET
	@Path("/getLists")
	public List<Lists> getLists(@QueryParam("boardName") String boardName)
	{
		return listDao.getList(boardName);
	}
	
	
	@DELETE
	@Path("/delete")
	public String deleteList(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName)
	{
		return listDao.deleteBoard(boardName, listName);
	}
	
	
	
	
	
	
	
}
