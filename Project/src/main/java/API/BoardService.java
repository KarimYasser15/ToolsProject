package API;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import java.security.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.ejb3.annotation.SecurityDomain;

import Database.BoardDao;
import EJBs.Board;
import EJBs.User;

@Stateless
@Path("/board")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@DeclareRoles("admin")
public class BoardService {

	@EJB
	private BoardDao boardDao;
	
	@POST
	@Path("/addBoard")
	//@RolesAllowed("admin")
	public String createBoard(Board board)
	{
		return boardDao.createBoard(board);
	}
	
	@DELETE
	@Path("/deleteBoard")
	public String deleteBoard(Board board)
	{
		return boardDao.deleteBoard(board);
	}
	
	@GET
	@Path("/viewBoard")
	public List<Board> viewBoard()
	{
		return boardDao.getAllBoards();
	}
	
	@POST
	@Path("/inviteCollaborator")
	public String inviteCollaborator(@QueryParam("board") String board, @QueryParam("collaborator") String collaborator)
	{
		System.out.println("BOARD NAME" + board);
		System.out.println("collaborator" + collaborator);
		return boardDao.inviteCollaborator(board, collaborator);
	}
	
	@GET
	@Path("/getCollaborators")
	public List<User> getCollaborators(@QueryParam("boardName") String boardName)
	{
		return boardDao.getCollaborators(boardName);
	}
	
	
	
}
