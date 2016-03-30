package projectireas.dao;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.CourseModel;

public class CourseDao extends AbstractDAO<CourseModel>{

	public CourseDao(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	public Optional<CourseModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public CourseModel create(CourseModel course) {
		return persist(course);
	}
}
