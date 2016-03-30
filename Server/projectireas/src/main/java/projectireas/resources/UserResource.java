package projectireas.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import projectireas.core.User;
import projectireas.dao.UserDao;
import projectireas.model.UserModel;

@Path("")
public class UserResource {

	private final UserDao userDao;

	public UserResource(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public User getUser(@PathParam("userId") LongParam userId) {
		return findSafely(userId.get());
	}

	private User findSafely(long userId) {
		final Optional<UserModel> userModelOptional = userDao.findById(userId);
		if (!userModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		UserModel model = userModelOptional.get();
		User user = new User(model.getName(),model.getRole(),model.getEmail(),model.getPassword());
		user.setId(model.getId());
		return user;
	}

	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public User  addUser(User user){
		return addSafely(user);
	}

	private User addSafely(User user) {
		UserModel userModel = new  UserModel(user.getName(),user.getRole(),user.getEmail(),user.getPassword());
		userModel = userDao.create(userModel);
		user.setId(userModel.getId());
		return user;
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public User  loginUser(User user){
		final Optional<UserModel> userModelOptional = userDao.findByEmail(user.getEmail());
		if(!userModelOptional.isPresent()){
			throw new NotFoundException("No Such User");
		}
		UserModel model = userModelOptional.get();
		if(!model.getPassword().equals(user.getPassword())){
			throw new NotFoundException("Incorrect Password");
		}
		User realUser = new User(model.getName(),model.getRole(),model.getEmail(),model.getPassword());
		realUser.setId(model.getId());
		return realUser;
	}
}