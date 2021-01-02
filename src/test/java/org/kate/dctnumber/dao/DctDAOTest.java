package org.kate.dctnumber.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.kate.dctnumber.model.Dct;
import org.kate.dctnumber.model.Employee;
import org.kate.env.TransactionManagerTest;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DctDAOTest extends TransactionManagerTest {

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

	@Test
	public void t01DctDAO() throws Exception {

		try {
			{
				UserTransaction tx = TM.getUserTransaction();
				tx.begin();

				EntityManager em = emf.createEntityManager();

				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				employeeDAO.setEntityManager(em);

				DctDAO dctDAO = new DctDAOImpl();
				dctDAO.setEntityManager(em);

				Employee signatory = new Employee();
                signatory.setGivenName("John");
                signatory.setSurName("Green");
                signatory.setPatronymic("G");
                
                signatory = employeeDAO.makePersistent(signatory);

                Employee performer = new Employee();
                performer.setGivenName("Семен");
                performer.setSurName("Петров");
                performer.setPatronymic("Петрович");

                performer = employeeDAO.makePersistent(performer);

                Employee addressee = new Employee();
                addressee.setGivenName("Лука");
                addressee.setSurName("Семенов");
                addressee.setPatronymic("Семенович");

                addressee = employeeDAO.makePersistent(addressee);

                Dct dct1 = new Dct();
                
                dct1.setNumber(2);
                dct1.setYear(2020);
                dct1.setSignatory(signatory);
                dct1.setPerformer(performer);
                dct1.setAddressee(addressee);

                dctDAO.makePersistent(dct1);

				tx.commit();
				em.close();
			}
			{
				UserTransaction tx = TM.getUserTransaction();
				tx.begin();

				EntityManager em = emf.createEntityManager();

				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				employeeDAO.setEntityManager(em);

				DctDAO dctDAO = new DctDAOImpl();
				dctDAO.setEntityManager(em);

                assertEquals(employeeDAO.getCount(), new Long(6));
                assertEquals(dctDAO.getCount(), new Long(2));

                List<Dct> dcts = dctDAO.findAll();

                assertEquals(dcts.size(), 2);
                assertEquals(dcts.get(1).getSignatory().getGivenName(), "John");
                assertEquals(dcts.get(1).getSignatory().getSurName(), "Green");
                assertEquals(dcts.get(1).getSignatory().getPatronymic(), "G");
                assertEquals(dcts.get(1).getPerformer().getGivenName(), "Семен");
                assertEquals(dcts.get(1).getPerformer().getSurName(), "Петров");
                assertEquals(dcts.get(1).getPerformer().getPatronymic(), "Петрович");
                assertEquals(dcts.get(1).getAddressee().getGivenName(), "Лука");
                assertEquals(dcts.get(1).getAddressee().getSurName(), "Семенов");
                assertEquals(dcts.get(1).getAddressee().getPatronymic(), "Семенович");

				tx.commit();
				em.close();
			}
			{
				UserTransaction tx = TM.getUserTransaction();
				tx.begin();

				EntityManager em = emf.createEntityManager();

				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				employeeDAO.setEntityManager(em);

				DctDAO dctDAO = new DctDAOImpl();
				dctDAO.setEntityManager(em);

                List<Dct> dcts = dctDAO.findAll();

                Employee signatory = dcts.get(1).getSignatory();
                signatory.setGivenName("Иван");
				signatory.setSurName("Зеленский");
				signatory.setPatronymic("Зеленович");
				
				employeeDAO.makePersistent(signatory);

				tx.commit();
				em.close();
			}
			{
				UserTransaction tx = TM.getUserTransaction();
				tx.begin();

				EntityManager em = emf.createEntityManager();

				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				employeeDAO.setEntityManager(em);

				DctDAO dctDAO = new DctDAOImpl();
				dctDAO.setEntityManager(em);

                List<Dct> dcts = dctDAO.findAll();

                assertEquals(dcts.size(), 2);
                assertEquals(dcts.get(1).getSignatory().getGivenName(), "Иван");
                assertEquals(dcts.get(1).getSignatory().getSurName(), "Зеленский");
                assertEquals(dcts.get(1).getSignatory().getPatronymic(), "Зеленович");
                assertEquals(dcts.get(1).getPerformer().getGivenName(), "Семен");
                assertEquals(dcts.get(1).getPerformer().getSurName(), "Петров");
                assertEquals(dcts.get(1).getPerformer().getPatronymic(), "Петрович");
                assertEquals(dcts.get(1).getAddressee().getGivenName(), "Лука");
                assertEquals(dcts.get(1).getAddressee().getSurName(), "Семенов");
                assertEquals(dcts.get(1).getAddressee().getPatronymic(), "Семенович");

				tx.commit();
				em.close();
			}

		} finally {
			TM.rollback();
		}
	}

}
