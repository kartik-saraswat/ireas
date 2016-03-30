package projectireas.dao;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.DepartmentModel;

public class DepartmentDao extends AbstractDAO<DepartmentModel> {

	public DepartmentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	public Optional<DepartmentModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}
	
	public DepartmentModel create(DepartmentModel department) {
		return persist(department);
	}
}
