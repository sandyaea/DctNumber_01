package org.kate.dctnumber.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.kate.dctnumber.model.Employee;
import org.kate.env.TransactionManagerTest;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EmployeeDAOTest extends TransactionManagerTest {

    // Static Entity Manager Factory per each test
	static public EntityManagerFactory emf;

    @BeforeTest()
    public void beforeTest() throws Exception {
    	emf = Persistence.createEntityManagerFactory("HelloWorldPU");
    }

	@AfterTest(alwaysRun = true)
    public void afterTest() throws Exception {
        if (emf != null)
            emf.close();
    }

	@Test
    public void t01EmployeeDAO() throws Exception {
		
        try {
        	{
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();
                
                EmployeeDAO employeeDAO = new EmployeeDAOImpl();
                employeeDAO.setEntityManager(em);

                Employee employee1 = new Employee();
                employee1.setGivenName("Михаил");
                employee1.setSurName("Петров");
                employee1.setPatronymic("П");
                
                employeeDAO.makePersistent(employee1);

                tx.commit();
                em.close();
        	}
        	{
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();
                
                EmployeeDAO employeeDAO = new EmployeeDAOImpl();
                employeeDAO.setEntityManager(em);

                assertEquals(employeeDAO.getCount(), new Long(4));
        	}

        } finally {
            TM.rollback();
        }
	}

}
