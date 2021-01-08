package org.kate.dctnumber.entity;

import org.kate.dctnumber.model.Dct;
import org.kate.env.TransactionManagerTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class UniqueNumberTest extends TransactionManagerTest {

	// Static Entity Manager Factory per each test
	static public EntityManagerFactory emf;

	@BeforeTest()
	public void beforeTest() throws Exception {
		emf = Persistence.createEntityManagerFactory("Test01PU");
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() throws Exception {
		if (emf != null)
			emf.close();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void t01Dct() throws Exception {

		Boolean isException = false;
		Integer numberOfDct = null;

		try {
			UserTransaction tx = TM.getUserTransaction();
			tx.begin();

			EntityManager em = emf.createEntityManager();

			List<Dct> dcts = em.createQuery("select d from Dct d").getResultList();

			numberOfDct = dcts.size();
			// There is at least one Dct.
			assertNotEquals(numberOfDct, new Integer(0));

			Dct dct1 = new Dct();

			dct1.setNumber(dcts.get(0).getNumber());
			dct1.setYear(dcts.get(0).getYear());
			dct1.setSignatory(dcts.get(0).getSignatory());
			dct1.setPerformer(dcts.get(0).getPerformer());
			dct1.setAddressee(dcts.get(0).getAddressee());

			em.persist(dct1);

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

			List<Dct> dcts = em.createQuery("select d from Dct d").getResultList();

			// Checking that the new Dct was NOT added.
			assertEquals(numberOfDct, new Integer(dcts.size()));

			tx.commit();
			em.close();

		} finally {
			TM.rollback();
		}
	}

}
