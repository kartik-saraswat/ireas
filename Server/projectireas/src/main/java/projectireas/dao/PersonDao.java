package projectireas.dao;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.PersonModel;

public class PersonDao  extends AbstractDAO<PersonModel> {

	public PersonDao(SessionFactory factory) {
		super(factory);
	}

	public Optional<PersonModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public PersonModel create(PersonModel person) {
		return persist(person);
	}
}