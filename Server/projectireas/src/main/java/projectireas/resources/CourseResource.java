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
import projectireas.core.Course;
import projectireas.dao.CourseDao;
import projectireas.model.CourseModel;

@Path("")
public class CourseResource {

	private final CourseDao courseDao;

	public CourseResource(CourseDao courseDao) {
		super();
		this.courseDao = courseDao;
	}
	
	@GET
	@Path("/course/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Course getProgram(@PathParam("courseId") LongParam courseId) {
		return findSafely(courseId.get());
	}

	private Course findSafely(long courseId) {
		final Optional<CourseModel> courseModelOptional = courseDao.findById(courseId);
		if (!courseModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		CourseModel model = courseModelOptional.get();
		Course course = new Course(model.getName(),model.getCode(),model.getProgramId());
		course.setId(model.getId());
		return course;
	}
	
	@POST
	@Path("/addCourse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Course  addCourse(Course course){
		return addSafely(course);
	}

	private Course addSafely(Course course) {
		CourseModel courseModel = new  CourseModel(course.getName(), course.getCode(),course.getProgramId());
		courseModel = courseDao.create(courseModel);
		course.setId(courseModel.getId());
		return course;
	}

	
}
