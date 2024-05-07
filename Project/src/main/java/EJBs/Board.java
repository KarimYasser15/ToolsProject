package EJBs;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String boardName;

	@ManyToMany
	private List<User> collaborators;
	
	@OneToMany
	private List<Lists> lists; 
	
	public Board()
	{
		
	}
	
	public Board(Board board)
	{
		this.boardName = board.getBoardName();
	}
	
	public String getBoardName() {
		return boardName;
	}
	
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	public void addCollaborator(User collaborator)
	{
		this.collaborators.add(collaborator);
	}
	
	public List<User> getCollaborators()
	{
		return collaborators;
	}

	public void setLists(Lists list) {
		this.lists.add(list);
	}
	
	public List<Lists> getLists() {
		return lists;
	}

	
}
