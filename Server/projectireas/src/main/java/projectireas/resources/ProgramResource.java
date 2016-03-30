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
import projectireas.core.Program;
import projectireas.dao.ProgramDao;
import projectireas.model.ProgramModel;

@Path("")
public class ProgramResource {

	private final ProgramDao programDao;

	public ProgramResource(ProgramDao programDao) {
		super();
		this.programDao = programDao;
	}

	@GET
	@Path("/program/{programId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Program getProgram(@PathParam("programId") LongParam programId) {
		return findSafely(programId.get());
	}

	private Program findSafely(long programId) {
		final Optional<ProgramModel> programModelOptional = programDao.findById(programId);
		if (!programModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		ProgramModel model = programModelOptional.get();
		Program program = new Program(model.getName(),model.getCode(),model.getDepartmentId());
		program.setId(model.getId());
		return program;
	}

	@POST
	@Path("/addProgram")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Program  addProgram(Program program){
		return addSafely(program);
	}

	private Program addSafely(Program program) {
		ProgramModel programModel = new  ProgramModel(program.getName(), program.getCode(),program.getDepartmentId());
		programModel = programDao.create(programModel);
		program.setId(programModel.getId());
		return program;
	}
}