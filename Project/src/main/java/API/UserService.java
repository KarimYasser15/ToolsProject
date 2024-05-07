package API;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Database.UserDatabase;
import EJBs.User;

@Stateless
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
	
	User userLogin = null;

	@EJB
	private UserDatabase userDB;
	
	@POST
	@Path("/register")
	public String registerUser(User user)
	{
		System.out.println("EMAIL" + user.getEmail());
		User userExists = userDB.findUser(user.getEmail());
		if(userExists == null)
		{
			User userNew = new User(user);
			userDB.registerUser(userNew);
			System.out.println("Register" + userNew.getName());
			return "User Registered Successfully";
		}
		else
		{
			return "User Already Exists";
		}
	}
		
		@GET
		@Path("/login")
		public String loginUser(User user)
		{
			User userExists = userDB.findUser(user.getEmail());
			System.out.println("EMAIL" + user.getEmail());
			if(userExists != null && userExists.getPassword().equals(user.getPassword()))
			{
				userLogin = userExists;
				return "Login Success!";
			}
			else if(userExists != null && !userExists.getPassword().equals(user.getPassword()))
			{
				return "Password Incorrect!";
			}
			else
			{
				return "User Doesn't Exist!";
			}
				
		}
		
		@PUT
		@Path("/update")
		public String updateUser(User user)
		{
			
			if(userLogin != null)
			{
				if(user.getName() != null)
				{
					userLogin.setName(user.getName());
				}
				if(user.getEmail() != null)
				{
					userLogin.setEmail(user.getEmail());
				}
				if(user.getPassword() != null)
				{
					userLogin.setPassword(user.getPassword());
				}
				userDB.updateUser(userLogin);
				return "User Updated Successfully!";
			}
			else
			{
				return "User Doesn't Exist!";
			}
			
		}
		
	
	
	
}
