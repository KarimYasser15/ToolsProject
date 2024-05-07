package Database;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import EJBs.Board;
import EJBs.Lists;
import EJBs.User;

@Stateless
public class ListsDao {
	
	@PersistenceContext(unitName = "hello")
	private EntityManager entityManager;
	
	@EJB
	private BoardDao boardDao;

	public String createList(String board, String list)
	{
		try
		{		
			Board boardNew = new Board();
			Lists newList = new Lists();
			newList.setListName(list);
			boardNew = boardDao.findBoard(board);
			if(boardNew == null)
			{
				return "Board Doesn't Exist";
			}
			newList.setBoard(boardNew);
			System.out.println("BOARDDD" + newList.getBoard().toString());
			System.out.println("BOARD NAME" + newList.getBoard().getBoardName());
			entityManager.persist(newList);
			boardNew.getLists().add(newList);
			entityManager.merge(boardNew);
		}catch(Exception e)
		{
			System.out.println("BOARD LIST" + board);
			return e.toString();
		}
		return "List Added";
	}
	
	public List<Lists> getList(String boardName)
	{
		try
		{
			
			Query query = entityManager.createQuery("SELECT l.listName FROM Lists l WHERE l.board.boardName = :boardName");
			query.setParameter("boardName" , boardName);
			List<Lists> lists = query.getResultList();
			System.out.println(lists.toString());
			System.out.println("D1TO");
			return lists;
		}catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	public String deleteBoard(String boardName, String listName)
	{
		Lists listFound = findList(boardName, listName);
		Board boardFound =  boardDao.findBoard(boardName);
		System.out.println("DELETE LIST");
		if(boardFound == null)
		{			
			return "Board Doesn't Exist";
		}
		else if(listFound == null)
		{
			return "List Doesn't Exist";
		}
		try {
			System.out.println("LIST NAME1" + listFound.getListName());
			boardFound.getLists().remove(listFound);
			entityManager.remove(entityManager.merge(listFound));
	        listFound = findList(boardName, listName);
			if(listFound == null)
			{			
				return "List Removed Successfully";
			}
			else
			{
				return "List Not Removed!";
			}
	    } catch (Exception e) {
	        return e.toString();
	    }
		
	}
	
	public Lists findList(String boardName, String listName)
	{
		Query query = entityManager.createQuery("SELECT l FROM Lists l WHERE l.board.boardName = :boardName AND l.listName = :listName");
		    query.setParameter("boardName", boardName);
		    query.setParameter("listName", listName);
		    try {
		    	Lists listFound = (Lists) query.getSingleResult();
		    	System.out.println("FIND LIST");
		    	System.out.println("FIND LIST NAME" + listFound.getListName());
		        return listFound;
		    } catch (NoResultException e) {
		        return null;
		    }
	}

	
	
	
	
}
