package org.kate.dctnumber.entity;

import org.kate.dctnumber.model.Employee;
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

public class EntityJPATest01 extends TransactionManagerTest {

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

	@SuppressWarnings("unchecked")
	@Test
    public void t01Employee() throws Exception {

            try {
            	{
                    UserTransaction tx = TM.getUserTransaction();
                    tx.begin();

                    EntityManager em = emf.createEntityManager();
            		
                    Employee employee1 = new Employee();
                    employee1.setGivenName("Михаил");
                    employee1.setSurName("Петров");
                    employee1.setPatronymic("П");
                    
                    em.persist(employee1);

                    tx.commit();
                    em.close();
            	}
            	{
                    UserTransaction tx = TM.getUserTransaction();
                    tx.begin();

                    EntityManager em = emf.createEntityManager();

                    List<Employee> employees =
                    		em.createQuery("select e from Employee e").getResultList();

                    assertEquals(employees.size(), 4);
            	
            	}

            } finally {
                TM.rollback();
            }
    }

    @Test
    public void t02Dct() throws Exception {

    }

}
