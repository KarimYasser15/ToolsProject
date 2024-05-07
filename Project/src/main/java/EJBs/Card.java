package EJBs;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String cardName;
	private String description;
	private String comment;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_id")
	private Lists list;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	
	@ManyToMany
	private List<User> assignedUsers;
	

	public Card()
	{
		
	}
	
	public Card(Card card)
	{
		this.cardName = card.getCardName();
		this.description = card.getDescription();
		this.comment = card.getComment();
	}
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public void setList(Lists list)
	{
		this.list = list;
	}
	
	public Lists getList()
	{
		return list;
	}
	
	public void assignUser(User user)
	{
		this.assignedUsers.add(user);
	}
	
	public List<User> getAssignedUsers()
	{
		return assignedUsers;
	}

	public String getComment() {
		return comment;
	}

	public void setComments(String comments) {
		this.comment = comments;
	}
	

	
	
	
	

}
