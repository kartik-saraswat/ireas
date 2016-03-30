package projectireas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Optional;

import io.dropwizard.hibernate.AbstractDAO;
import projectireas.model.ExamModel;

public class ExamDao  extends AbstractDAO<ExamModel>{

	public ExamDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<ExamModel> findById(Long id) {
		return Optional.fromNullable(get(id));
	}
	
	public List<ExamModel> findExamsByInvigilatorId(Long invigilatorId) {
		
		Criteria criteria = currentSession().createCriteria(ExamModel.class);
		criteria.add(Restrictions.eq("invigilatorId", invigilatorId));
		List<ExamModel> examModelList = (List<ExamModel>) criteria.list();
		return examModelList;
 	}
	
	public ExamModel create(ExamModel exam) {
		return persist(exam);
	}
}
