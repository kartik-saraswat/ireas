package projectireas.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.UserModel;

public class UserDao extends AbstractDAO<UserModel> {

	public UserDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<UserModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Optional<UserModel> findByEmail(String email) {
		Criteria criteria = currentSession().createCriteria(UserModel.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.setMaxResults(1);
		return Optional.fromNullable((UserModel)criteria.uniqueResult());
	}

	public UserModel create(UserModel user) {
		return persist(user);
	}
}
