package org.jpwh.helloworld;

import org.kate.dctnumber.model.Dct;
import org.kate.dctnumber.model.Employee;
import org.kate.env.TransactionManagerTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DctJPATest extends TransactionManagerTest {

    @SuppressWarnings("unchecked")
	@Test
    public void t01FirstEmployee() throws Exception {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("HelloWorldPU");

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
                emf.close();
            }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void t02FirstDct() throws Exception {

        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("HelloWorldPU");

        try {
            {
                /* 
                    Get access to the standard transaction API <code>UserTransaction</code> and
                    begin a transaction on this thread of execution.
                 */
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                /* 
                    Begin a new session with the database by creating an <code>EntityManager</code>, this
                    is your context for all persistence operations.
                 */
                EntityManager em = emf.createEntityManager();

                Employee signatory = new Employee();
                signatory.setGivenName("John");
                signatory.setSurName("Green");
                signatory.setPatronymic("G");
                
                em.persist(signatory);

                Employee performer = new Employee();
                performer.setGivenName("Семен");
                performer.setSurName("Петров");
                performer.setPatronymic("Петрович");

                em.persist(performer);

                Employee addressee = new Employee();
                addressee.setGivenName("Лука");
                addressee.setSurName("Семенов");
                addressee.setPatronymic("Семенович");

                em.persist(addressee);

                Dct dct1 = new Dct();
                
                dct1.setNumber(2);
                dct1.setYear(2020);
                dct1.setSignatory(signatory);
                dct1.setPerformer(performer);
                dct1.setAddressee(addressee);

                em.persist(dct1);


                /* 
                    Commit the transaction, Hibernate now automatically checks the persistence context and
                    executes the necessary SQL <code>INSERT</code> statement.
                 */
                tx.commit();

                /* 
                    If you create an <code>EntityManager</code>, you must close it.
                 */
                em.close();
            }

            {
                /* 
                    Every interaction with your database should occur within explicit transaction boundaries,
                    even if you are only reading data.
                 */
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

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
                /* 
                    Every interaction with your database should occur within explicit transaction boundaries,
                    even if you are only reading data.
                 */
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

                assertEquals(dcts.size(), 2);

                dcts.get(1).getSignatory().setGivenName("Иван");
                dcts.get(1).getSignatory().setSurName("Зеленский");
                dcts.get(1).getSignatory().setPatronymic("Зеленович");

                /* 
                    On commit, Hibernate checks the persistence context for dirty state and executes the
                    SQL <code>UPDATE</code> automatically to synchronize the in-memory with the database state.
                 */
                tx.commit();

                em.close();
            }


            {
                /* 
                    Every interaction with your database should occur within explicit transaction boundaries,
                    even if you are only reading data.
                 */
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

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
            emf.close();
        }
    }

}
