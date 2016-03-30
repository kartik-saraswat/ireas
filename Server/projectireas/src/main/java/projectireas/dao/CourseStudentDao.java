package projectireas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.CourseStudentModel;

public class CourseStudentDao  extends AbstractDAO<CourseStudentModel>{

	public CourseStudentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

public List<CourseStudentModel> findStudentsByCourseId(Long courseId) {
		
		Criteria criteria = currentSession().createCriteria(CourseStudentModel.class);
		criteria.add(Restrictions.eq("courseId", courseId));
		List<CourseStudentModel> studentCourseModelList = (List<CourseStudentModel>) criteria.list();
		return studentCourseModelList;
 	}
}
