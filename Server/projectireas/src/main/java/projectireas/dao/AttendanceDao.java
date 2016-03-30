package projectireas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.AttendanceModel;

public class AttendanceDao  extends AbstractDAO<AttendanceModel> {

	public AttendanceDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<AttendanceModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public AttendanceModel create(AttendanceModel attendance) {
		return persist(attendance);
	}
	
	public AttendanceModel updateAttendance(AttendanceModel attendance) {
		return persist(attendance);
	}
	
	
public List<AttendanceModel> findAttendancesByExamId(Long examId) {
		
		Criteria criteria = currentSession().createCriteria(AttendanceModel.class);
		criteria.add(Restrictions.eq("examId", examId));
		List<AttendanceModel> studentCourseModelList = (List<AttendanceModel>) criteria.list();
		return studentCourseModelList;
 	}

public AttendanceModel findAttendancesByExamIdAndStudent(Long examId, Long studentId) {
	
	Criteria criteria = currentSession().createCriteria(AttendanceModel.class);
	criteria.add(Restrictions.eq("examId", examId));
	criteria.add(Restrictions.eq("studentId", studentId));
	List<AttendanceModel> studentCourseModelList = (List<AttendanceModel>) criteria.list();
	return studentCourseModelList.get(0);
	}
	
}
