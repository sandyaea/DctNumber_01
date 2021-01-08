package org.kate.dctnumber.dao;

import org.kate.dctnumber.model.Dct;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class DctDAOImpl extends GenericDAOImpl<Dct, Long> implements DctDAO {

	public DctDAOImpl() {
		super(Dct.class);
	}

	/*
	 * If there are no documents for the selected year, then Integer zero is
	 * returned.
	 */
	public Integer maxNumberOfDct(Integer year) {
		Integer maxNumber;
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Object> criteria = cb.createQuery();
		Root<Dct> d = criteria.from(Dct.class);
		criteria.select(cb.max(d.<Integer>get("number")));
		criteria.where(cb.equal(d.<Integer>get("year"), year));
		maxNumber = (Integer) em.createQuery(criteria).getSingleResult();
		if (maxNumber == null)
			maxNumber = 0;

		return maxNumber;
	}
}
