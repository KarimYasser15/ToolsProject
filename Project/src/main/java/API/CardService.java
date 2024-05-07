package API;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import Database.CardDao;
import EJBs.Card;
import EJBs.User;

@Stateless
@Path("/card")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardService {
	
	@EJB
	private CardDao cardDao;
	
	@POST
	@Path("/create")
	public String createCard(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName, Card card)
	{
		return cardDao.createCard(boardName, listName, card);
	}
	
	@PUT
	@Path("/move")
	public String moveCard(@QueryParam("boardName") String boardName, @QueryParam("fromListName") String fromListName, @QueryParam("toListName") String toListName ,@QueryParam("cardName") String cardName)
	{
		return cardDao.moveCard(boardName, fromListName, toListName, cardName);
	}
	
	@GET
	@Path("/getCards")
	public List<Card> getCards(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName)
	{
		return cardDao.getCards(boardName, listName);
	}
	
	@POST
	@Path("/assign")
	public String assignUsers(@QueryParam("userEmail") String userEmail,@QueryParam("boardName") String boardName, @QueryParam("listName") String listName ,@QueryParam("cardName") String cardName)
	{
		return cardDao.assignUsers(userEmail ,boardName, listName, cardName);
	}
	
	@GET
	@Path("/getAssignedUsers")
	public List<User> getAssignedUsers(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName ,@QueryParam("cardName") String cardName)
	{
		return cardDao.getAssignedUsers(boardName, listName, cardName);
	}
	
	@GET
	@Path("/getCommentAndDescription")
	public List<String> getCommentAndDescription(@QueryParam("boardName") String boardName, @QueryParam("listName") String listName ,@QueryParam("cardName") String cardName)
	{
		return cardDao.getCommentAndDescription(boardName, listName, cardName);
	}
	

}
