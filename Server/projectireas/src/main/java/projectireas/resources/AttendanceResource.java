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
import projectireas.core.Attendance;
import projectireas.core.Exam;
import projectireas.core.ExamAttendance;
import projectireas.core.StudentAttendance;
import projectireas.core.User;
import projectireas.dao.AttendanceDao;
import projectireas.dao.ExamDao;
import projectireas.dao.UserDao;
import projectireas.model.AttendanceModel;
import projectireas.model.ExamModel;
import projectireas.model.UserModel;

@Path("")
public class AttendanceResource {

	private final AttendanceDao attendanceDao;
	private final ExamDao examDao;
	private final UserDao userDao;

	public AttendanceResource(AttendanceDao attendanceDao, ExamDao examDao, UserDao  userDao) {
		super();
		this.attendanceDao = attendanceDao;
		this.examDao = examDao;
		this.userDao = userDao;
	}

	@GET
	@Path("/attendance/{attendanceId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Attendance getAttendance(@PathParam("attendanceId") LongParam attendanceId) {
		return findSafely(attendanceId.get());
	}

	private Attendance findSafely(long attendanceId) {
		final Optional<AttendanceModel> attendanceModelOptional = attendanceDao.findById(attendanceId);
		if (!attendanceModelOptional.isPresent()) {
			throw new NotFoundException("No such user.");
		}
		AttendanceModel model = attendanceModelOptional.get();
		Attendance attendance = new Attendance(model.getExamId(), model.getStudentId(), model.getAttendance());
		attendance.setId(model.getId());
		return attendance;
	}

	@POST
	@Path("/addAttendance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Attendance  addAttendace(Attendance attendance){
		return addSafely(attendance);
	}

	private Attendance addSafely(Attendance attendance) {
		AttendanceModel attendanceModel = new  AttendanceModel(attendance.getExamId(), attendance.getStudentId(), attendance.getAttendance());
		attendanceModel = attendanceDao.create(attendanceModel);
		attendance.setId(attendanceModel.getId());
		return attendance;
	}

	@GET
	@Path("/attendance/exam/{examId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public ExamAttendance getExamAttendance(@PathParam("examId") LongParam examId) {
		return findExamAttendance(examId.get());
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
