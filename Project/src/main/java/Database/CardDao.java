package Database;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import EJBs.Board;
import EJBs.Card;
import EJBs.Lists;
import EJBs.User;

@Stateless
public class CardDao {
	
	@PersistenceContext(unitName = "hello")
	private EntityManager entityManager;
	
	@EJB
	private BoardDao boardDao;
	
	@EJB
	private ListsDao listsDao;
	
	@EJB
	private UserDatabase userDao;
	
	
	
	public String createCard(String boardName, String listName, Card card)
	{
		
		Board boardFound = boardDao.findBoard(boardName);
		Lists listFound = listsDao.findList(boardName, listName);
		if(boardFound == null || listFound == null)
		{
			return "Board or List Doesn't Exists";
		}
		try
		{
			
			Card cardFound = findCard(boardName, listName, card.getCardName());
			if(cardFound != null)
			{
				return "Card With Same Name Already Exists";
			}
			cardFound = new Card(card);
			cardFound.setBoard(boardFound);
			cardFound.setList(listFound);
			System.out.println("CARD BOARD " + cardFound.getBoard().getBoardName());
			System.out.println("CARD LIST " + cardFound.getList().getListName());
			entityManager.persist(cardFound);
			System.out.println("TEST PERSIST");
			listFound.getCards().add(cardFound);
			System.out.println("TEST ADDED CARD");
			entityManager.merge(listFound);
			cardFound = findCard(boardName, listName, card.getCardName());
			if(cardFound == null)
			{
				return "Card Not ADDED!";
			}
			return "Card Added!";
		}
		catch(Exception e)
		{
			return e.toString();
		}
		
	}
	
	
	public Card findCard(String boardName, String listName, String cardName)
	{
		Query query = entityManager.createQuery("Select c FROM Card c where c.board.boardName = :boardName AND c.list.listName = :listName AND c.cardName = :cardName");
	    query.setParameter("boardName", boardName);
	    query.setParameter("listName", listName);
	    query.setParameter("cardName" , cardName);
	    try {
	    	Card cardFound = (Card) query.getSingleResult();
	    	System.out.println("FIND Card");
	    	System.out.println("FIND LIST NAME" + cardFound.getCardName());
	        return cardFound;
	    } catch (NoResultException e) {
	        return null;
	    }
		
	}
	
	public String moveCard(String boardName ,String fromListName, String toListName, String cardName)
	{
		Lists fromList = listsDao.findList(boardName, fromListName);
		Lists toList = listsDao.findList(boardName, toListName);
		Card cardFound = findCard(boardName, fromListName, cardName);
		if(fromList == null || toList == null || cardFound == null)
		{
			return "List or Card Not Found!";
		}
		cardFound.setList(toList);
		fromList.getCards().remove(cardFound);
		toList.getCards().add(cardFound);
		entityManager.merge(fromList);
	    entityManager.merge(toList);
		
	    return "Card Moved to List: "+ toList.getListName();	
	}
	
	public List<Card> getCards(String boardName, String listName)
	{
	    try {
	    	System.out.println("TEST");
	    	Query query = entityManager.createQuery("Select c.cardName FROM Card c where c.board.boardName = :boardName AND c.list.listName = :listName");
	    	System.out.println("TES2");
	    	query.setParameter("boardName", boardName);
	    	System.out.println("TEST3");
	    	query.setParameter("listName", listName);
	    	System.out.println("TEST4");
	    	List<Card> cards = query.getResultList();
	    	System.out.println("TEST5" + cards);
	    	return cards;
	    } catch (Exception e) {
			System.out.println(e.toString());
	    	return null;
	    }
	}
	
	public String assignUsers(String userEmail, String boardName, String listName, String cardName)
	{
		Board boardFound = boardDao.findBoard(boardName);
		User userFound = userDao.findUser(userEmail);
		Card cardFound = findCard(boardName, listName, cardName);
		if(!boardFound.getCollaborators().contains(userFound))
		{
			return "User isn't a collaborator in this board!";
		}
		if(cardFound.getAssignedUsers().contains(userFound))
		{
			return "User is already Assigned to this Card!";
		}
		try
		{
			System.out.println("Entered Try");
			cardFound.getAssignedUsers().add(userFound);
			System.out.println("Merge Test");
			entityManager.merge(cardFound);
			System.out.println("Merge Passed");
			if(cardFound.getAssignedUsers().contains(userFound))
			{
				return "User Added!";
			}
			return "User Not Added!";
		}catch(Exception e)
		{
			return e.toString();
		}
		
	}
	
	public List<User> getAssignedUsers(String boardName, String listName, String cardName)
	{
		try
		{
		System.out.println("TEST");
    	Query query = entityManager.createQuery("Select c.assignedUsers FROM Card c where c.board.boardName = :boardName AND c.list.listName = :listName AND c.cardName = :cardName");
    	System.out.println("TES2");
    	query.setParameter("boardName", boardName);
    	System.out.println("TEST3");
    	query.setParameter("listName", listName);
    	System.out.println("TEST4");
    	query.setParameter("cardName", cardName);
    	List<User> assignedUsers = query.getResultList();
    	return assignedUsers;
		}catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
	}
	
	public List<String> getCommentAndDescription(String boardName, String listName, String cardName)
	{
		try
		{
		System.out.println("TEST");
    	Query query = entityManager.createQuery("Select c.description , c.comment FROM Card c where c.board.boardName = :boardName AND c.list.listName = :listName AND c.cardName = :cardName");
    	System.out.println("TES2");
    	query.setParameter("boardName", boardName);
    	System.out.println("TEST3");
    	query.setParameter("listName", listName);
    	System.out.println("TEST4");
    	query.setParameter("cardName", cardName);
    	List<String> data =  query.getResultList();
    	return data;
		}catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
