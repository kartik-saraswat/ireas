package projectireas.dao;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.ProgramModel;

public class ProgramDao extends AbstractDAO<ProgramModel>{

	public ProgramDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<ProgramModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public ProgramModel create(ProgramModel program) {
		return persist(program);
	}
}
