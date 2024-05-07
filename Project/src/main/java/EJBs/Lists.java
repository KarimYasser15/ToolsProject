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

@Entity
public class Lists {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String listName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	@ManyToMany
	private List<Card> card;
	
	public Lists() {}
	
	public Lists(Lists listName)
	{
		this.setListName(listName.getListName());
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListName() {
		return listName;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public void setCard(Card card)
	{
		this.card.add(card);
	}
	public List<Card> getCards()
	{
		return card;
	}



	
}
