package projectireas;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import projectireas.dao.AttendanceDao;
import projectireas.dao.CourseDao;
import projectireas.dao.CourseStudentDao;
import projectireas.dao.DepartmentDao;
import projectireas.dao.ExamDao;
import projectireas.dao.PersonDao;
import projectireas.dao.ProgramDao;
import projectireas.dao.UserDao;
import projectireas.model.AttendanceModel;
import projectireas.model.CourseModel;
import projectireas.model.CourseStudentModel;
import projectireas.model.DepartmentModel;
import projectireas.model.ExamModel;
import projectireas.model.PersonModel;
import projectireas.model.ProgramModel;
import projectireas.model.UserModel;
import projectireas.resources.AttendanceResource;
import projectireas.resources.CourseResource;
import projectireas.resources.DepartmentResource;
import projectireas.resources.ExamResource;
import projectireas.resources.HelloWorldResource;
import projectireas.resources.ImageResource;
import projectireas.resources.NotificationResource;
import projectireas.resources.PersonResource;
import projectireas.resources.ProgramResource;
import projectireas.resources.UserResource;


public class ProjectIreasApplication extends Application<ProjectIreasConfiguration>{

	private final HibernateBundle<ProjectIreasConfiguration> hibernate = new HibernateBundle<ProjectIreasConfiguration>
	(PersonModel.class,UserModel.class, DepartmentModel.class,ProgramModel.class, 
			CourseModel.class, ExamModel.class, AttendanceModel.class, CourseStudentModel.class){

		public DataSourceFactory getDataSourceFactory(ProjectIreasConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};	 

	public static void main(String[] args) throws Exception {
		new ProjectIreasApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ProjectIreasConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new ViewBundle<ProjectIreasConfiguration>());
		bootstrap.addBundle(new AssetsBundle());
		bootstrap.addBundle(new MultiPartBundle());
	}

	@Override
	public void run(ProjectIreasConfiguration configuration,
			Environment environment) {

		// Enable CORS headers
		final FilterRegistration.Dynamic cors =
				environment.servlets().addFilter("CORS", CrossOriginFilter.class);
		// Configure CORS parameters
		cors.setInitParameter("allowedOrigins", "*");
		cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		final HelloWorldResource resource = new HelloWorldResource(
				configuration.getTemplate(),
				configuration.getDefaultName()
				);
		environment.jersey().register(resource);

		final PersonDao personDao = new PersonDao(hibernate.getSessionFactory());
		environment.jersey().register(new PersonResource(personDao));

		final UserDao userDao =  new UserDao(hibernate.getSessionFactory());
		environment.jersey().register(new UserResource(userDao));

		final DepartmentDao departmentDao =  new DepartmentDao(hibernate.getSessionFactory());
		environment.jersey().register(new DepartmentResource(departmentDao));

		final ProgramDao programDao =  new ProgramDao(hibernate.getSessionFactory());
		environment.jersey().register(new ProgramResource(programDao));

		final CourseDao courseDao =  new CourseDao(hibernate.getSessionFactory());
		environment.jersey().register(new CourseResource(courseDao));

		final ExamDao examDao =  new ExamDao(hibernate.getSessionFactory());
		final CourseStudentDao courseStudentDao =  new CourseStudentDao(hibernate.getSessionFactory());
		
		final AttendanceDao attendanceDao =  new AttendanceDao(hibernate.getSessionFactory());
		environment.jersey().register(new ExamResource(examDao, userDao, courseStudentDao, attendanceDao));
		environment.jersey().register(new AttendanceResource(attendanceDao, examDao, userDao));

		environment.jersey().register(new NotificationResource(userDao, examDao, courseDao));
		environment.jersey().register(new ImageResource(attendanceDao));
	}
}
