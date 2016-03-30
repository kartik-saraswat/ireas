package projectireas.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import projectireas.core.Course;
import projectireas.core.Exam;
import projectireas.core.Notification;
import projectireas.core.NotificationList;
import projectireas.core.User;
import projectireas.dao.CourseDao;
import projectireas.dao.ExamDao;
import projectireas.dao.UserDao;
import projectireas.model.CourseModel;
import projectireas.model.ExamModel;
import projectireas.model.UserModel;

@Path("")
public class NotificationResource {

	private UserDao userDao;
	private ExamDao examDao;
	private CourseDao courseDao;
	public NotificationResource(UserDao userDao, ExamDao examDao, CourseDao courseDao) {
		super();
		this.userDao = userDao;
		this.examDao = examDao;
		this.courseDao = courseDao;
	}

	@GET
	@Path("/notifications/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public NotificationList getNotification(@PathParam("userId") LongParam userId) {
		return findSafely(userId.get());
	}

	private NotificationList findSafely(long userId) {
		final Optional<UserModel> userModelOptional = userDao.findById(userId);
		if (!userModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}

		UserModel model = userModelOptional.get();
		User user = new User(model.getName(),model.getRole(),model.getEmail(),model.getPassword());
		user.setId(model.getId());

		List<ExamModel> examModelList = examDao.findExamsByInvigilatorId(userId);
		List<Notification> notificationList = Lists.newArrayList();
		for(ExamModel examModel : examModelList) {
			final Optional<CourseModel> courseModelOptional = courseDao.findById(examModel.getCourseId());
			if (!courseModelOptional.isPresent()) {
				throw new NotFoundException("No such user.");
			}

			CourseModel courseModel = courseModelOptional.get();
			Course course = new Course(courseModel.getName(),courseModel.getCode(),courseModel.getProgramId());
			course.setId(courseModel.getId());
			Exam exam = new Exam(examModel.getCourseId(), examModel.getInvigilatorId(), examModel.getExamStartTime(), examModel.getExamTypeId(), examModel.getRoom());
			exam.setId(model.getId());
			notificationList.add(new Notification(course,exam));
		}
		return new NotificationList(userId, notificationList);
	}
}