package org.kate.dctnumber.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.kate.dctnumber.model.Dct;
import org.kate.env.TransactionManagerTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MaxNumberOfDctTest extends TransactionManagerTest {

	// Static Entity Manager Factory per each test
	static public EntityManagerFactory emf;

	@BeforeTest()
	public void beforeTest() throws Exception {
		emf = Persistence.createEntityManagerFactory("MaxNumberOfDct01PU");
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() throws Exception {
		if (emf != null)
			emf.close();
	}

	@Test
	public void t01MaxNumberOfDct() throws Exception {

		Boolean isException = false;
		Long numberOfDct;

		try {
			UserTransaction tx = TM.getUserTransaction();
			tx.begin();

			EntityManager em = emf.createEntityManager();

			DctDAO dctDAO = new DctDAOImpl();
			dctDAO.setEntityManager(em);

			assertEquals(dctDAO.maxNumberOfDct(2031), new Integer(0));

			Integer maxNumberOfDct = dctDAO.maxNumberOfDct(2020);

			numberOfDct = dctDAO.getCount();

			List<Dct> dcts = dctDAO.findAll();

			Dct dct1 = new Dct();

			dct1.setNumber(maxNumberOfDct + 1);
			dct1.setYear(2020);
			dct1.setSignatory(dcts.get(0).getSignatory());
			dct1.setPerformer(dcts.get(0).getPerformer());
			dct1.setAddressee(dcts.get(0).getAddressee());

			dctDAO.makePersistent(dct1);

			tx.commit();
			em.close();
			numberOfDct++;

		} finally {
			TM.rollback();
		}

		try {
			UserTransaction tx = TM.getUserTransaction();
			tx.begin();

			EntityManager em = emf.createEntityManager();

			DctDAO dctDAO = new DctDAOImpl();
			dctDAO.setEntityManager(em);

			assertEquals(dctDAO.getCount(), numberOfDct);

			tx.commit();
			em.close();

		} finally {
			TM.rollback();
		}

		try {
			UserTransaction tx = TM.getUserTransaction();
			tx.begin();

			EntityManager em = emf.createEntityManager();

			DctDAO dctDAO = new DctDAOImpl();
			dctDAO.setEntityManager(em);

			Integer maxNumberOfDct = dctDAO.maxNumberOfDct(2020);

			List<Dct> dcts = dctDAO.findAll();

			Dct dct1 = new Dct();

			dct1.setNumber(maxNumberOfDct);
			dct1.setYear(2020);
			dct1.setSignatory(dcts.get(0).getSignatory());
			dct1.setPerformer(dcts.get(0).getPerformer());
			dct1.setAddressee(dcts.get(0).getAddressee());

			dctDAO.makePersistent(dct1);

			tx.commit();
			em.close();

		} catch (Exception ex) {
			isException = true;
		} finally {
			TM.rollback();
		}

		assertTrue(isException);

		try {
			UserTransaction tx = TM.getUserTransaction();
			tx.begin();

			EntityManager em = emf.createEntityManager();

			DctDAO dctDAO = new DctDAOImpl();
			dctDAO.setEntityManager(em);

			assertEquals(dctDAO.getCount(), numberOfDct);

			tx.commit();
			em.close();

		} finally {
			TM.rollback();
		}

	}

}
