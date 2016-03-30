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
import projectireas.dao.PersonDao;
import projectireas.core.Person;
import projectireas.model.PersonModel;
import projectireas.views.PersonView;

@Path("")
public class PersonResource {
	private final PersonDao personDao;

	public PersonResource(PersonDao personDao) {
		this.personDao = personDao;
	}

	@GET
	@Path("/person/{personId}")
	@Produces(MediaType.TEXT_HTML)
	@UnitOfWork
	public PersonView getPerson(@PathParam("personId") LongParam personId) {
		return new PersonView(findSafely(personId.get()));
	}


	@POST
	@Path("/addPerson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	@UnitOfWork
	public PersonView addPerson(Person person) {
		return new PersonView(addSafely(person));
	}

	private Person addSafely(Person person){

		PersonModel model=new PersonModel(person.getFullName(), person.getJobTitle());
		model = personDao.create(model);
		person.setId(model.getId());
		return person;
	}


	private Person findSafely(long personId) {
		final Optional<PersonModel> personModel = personDao.findById(personId);
		if (!personModel.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		Person person = new Person(personModel.get().getFullName(),personModel.get().getJobTitle());
		return person;
	}
}
