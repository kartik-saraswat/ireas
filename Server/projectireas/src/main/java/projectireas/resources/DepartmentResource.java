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
import projectireas.core.Department;
import projectireas.dao.DepartmentDao;
import projectireas.model.DepartmentModel;

@Path("")
public class DepartmentResource {

	private final DepartmentDao departmentDao;

	public DepartmentResource(DepartmentDao departmentDao) {
		super();
		this.departmentDao = departmentDao;
	}

	@GET
	@Path("/department/{departmentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Department getDepartment(@PathParam("departmentId") LongParam departmentId) {
		return findSafely(departmentId.get());
	}

	private Department findSafely(long departmentId) {
		final Optional<DepartmentModel> departmentModelOptional = departmentDao.findById(departmentId);
		if (!departmentModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		DepartmentModel model = departmentModelOptional.get();
		Department department = new Department(model.getName(), model.getCode());
		department.setId(model.getId());
		return department;
	}

	@POST
	@Path("/addDepartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Department  addDepartment(Department department){
		return addSafely(department);
	}

	private Department addSafely(Department department) {
		DepartmentModel departmentModel = new  DepartmentModel(department.getName(),department.getCode());
		departmentModel = departmentDao.create(departmentModel);
		department.setId(departmentModel.getId());
		return department;
	}


}
