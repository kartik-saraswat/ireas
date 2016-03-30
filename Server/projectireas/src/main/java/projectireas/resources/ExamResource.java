package projectireas.resources;

import java.util.List;

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
import jersey.repackaged.com.google.common.collect.Lists;
import projectireas.core.Exam;
import projectireas.core.ExamAttendance;
import projectireas.core.StudentAttendance;
import projectireas.core.User;
import projectireas.dao.AttendanceDao;
import projectireas.dao.CourseStudentDao;
import projectireas.dao.ExamDao;
import projectireas.dao.UserDao;
import projectireas.model.AttendanceModel;
import projectireas.model.CourseStudentModel;
import projectireas.model.ExamModel;
import projectireas.model.UserModel;

@Path("")
public class ExamResource {

	private final ExamDao examDao;
	private final UserDao userDao;
	private final CourseStudentDao courseStudentDao;
	private final AttendanceDao attendanceDao;

	public ExamResource(ExamDao examDao, UserDao userDao, CourseStudentDao courseStudentDao, AttendanceDao attendanceDao) {
		super();
		this.examDao = examDao;
		this.userDao = userDao;
		this.courseStudentDao = courseStudentDao;
		this.attendanceDao = attendanceDao;
	}

	@GET
	@Path("/exam/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Exam getExam(@PathParam("examId") LongParam examId) {
		return findSafely(examId.get());
	}

	private Exam findSafely(long examId) {
		final Optional<ExamModel> examModelOptional = examDao.findById(examId);
		if (!examModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		ExamModel model = examModelOptional.get();
		Exam exam = new Exam(model.getCourseId(), model.getInvigilatorId(), model.getExamStartTime(), model.getExamTypeId(), model.getRoom());
		exam.setId(model.getId());
		return exam;
	}

	@POST
	@Path("/addExam")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Exam  addExam(Exam exam){
		return addSafely(exam);
	}

	private Exam addSafely(Exam exam) {
		ExamModel examModel = new  ExamModel(exam.getCourseId(), exam.getInvigilatorId(), exam.getExamStartTime(), exam.getExamTypeId(), exam.getRoom());
		examModel = examDao.create(examModel);
		exam.setId(examModel.getId());
		return exam;
	}

	@GET
	@Path("/exam/{examId}/students")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public ExamAttendance getStudents(@PathParam("examId") LongParam examId) {
		return findExamAttendance(examId.get());
	}

	private List<User> findStudents(long examId) {
		final Optional<ExamModel> examModelOptional = examDao.findById(examId);
		if (!examModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		ExamModel model = examModelOptional.get();
		List<CourseStudentModel> courseStudentModelList = courseStudentDao.findStudentsByCourseId(model.getCourseId());
		List<User> studentList = Lists.newArrayList();
		for(CourseStudentModel courseStudentModel : courseStudentModelList){
			final Optional<UserModel> userModelOptional = userDao.findById(courseStudentModel.getStudentId());
			if (!userModelOptional.isPresent()) {
				throw new NotFoundException("No such user.");
			}
			UserModel userModel = userModelOptional.get();
			User user = new User(userModel.getName(),userModel.getRole(),userModel.getEmail(),userModel.getPassword());
			user.setId(userModel.getId());
			studentList.add(user);
		}

		return studentList;
	}
	
	private ExamAttendance findExamAttendance(long examId) {

		final Optional<ExamModel> examModelOptional = examDao.findById(examId);
		if (!examModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		ExamModel examModel = examModelOptional.get();
		Exam exam = new Exam(examModel.getCourseId(), examModel.getInvigilatorId(), examModel.getExamStartTime(), examModel.getExamTypeId(), examModel.getRoom());
		exam.setId(examModel.getId());

		List<AttendanceModel> attendanceModelList = attendanceDao.findAttendancesByExamId(examId);
		List<StudentAttendance> studentAttendanceList = Lists.newArrayList();
		for(AttendanceModel attendanceModel : attendanceModelList){
			final Optional<UserModel> userModelOptional = userDao.findById(attendanceModel.getStudentId());
			if (!userModelOptional.isPresent()) {
				throw new NotFoundException("No such user.");
			}
			UserModel userModel = userModelOptional.get();
			User user = new User(userModel.getName(),userModel.getRole(),userModel.getEmail(),userModel.getPassword());
			user.setId(userModel.getId());
			studentAttendanceList.add(new StudentAttendance(user,attendanceModel.getAttendance()));
		}
		return new ExamAttendance(exam, studentAttendanceList);
	}
}