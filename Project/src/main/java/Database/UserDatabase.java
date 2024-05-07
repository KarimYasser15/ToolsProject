package Database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import EJBs.User;

@Stateless
public class UserDatabase {
	
	@PersistenceContext(unitName = "hello")
	private EntityManager entityManager;
	
	public void registerUser(User user)
	{
		entityManager.persist(user);
	}

	public User findUser(String email) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            // Handle exceptions if user not found
            return null;
        }
    }

	
	public void updateUser(User user)
	{
		entityManager.merge(user);
		
	}
	

}
