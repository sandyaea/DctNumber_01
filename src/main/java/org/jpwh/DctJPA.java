package org.jpwh;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.kate.dctnumber.model.Dct;
import org.kate.dctnumber.model.Employee;
import org.kate.env.DatabaseProduct;
import org.kate.env.TransactionManagerSetup;

public class DctJPA {
	
	static public TransactionManagerSetup TM;
	
	public static void main(String[] args) {

		String database = "MySQL";
		String connectionURL = "jdbc:mysql://localhost/DctNumber_01?useSSL=false";

		System.out.println("Hello World!");
		
		try {
			TM = new TransactionManagerSetup(
					database != null
			        	? DatabaseProduct.valueOf(database.toUpperCase(Locale.US))
			        	: DatabaseProduct.H2, 
					connectionURL
			);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		DctJPA dctJPA = new DctJPA();
		try {
			dctJPA.storeLoadMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (TM != null)
			try {
				TM.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

    @SuppressWarnings("unchecked")
	public void storeLoadMessage() throws Exception {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("Test00PU");
        
        try {
        	{
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();
                EntityManager em = emf.createEntityManager();

                Employee signatory = new Employee();
                signatory.setGivenName("John");
                signatory.setSurName("Green");
                
                em.persist(signatory);

                Employee performer = new Employee();
                performer.setGivenName("Семен");
                performer.setSurName("Петров");

                em.persist(performer);

                Employee addressee = new Employee();
                addressee.setGivenName("Лука");
                addressee.setSurName("Семенов");

                em.persist(addressee);

                Dct dct1 = new Dct();
                
                dct1.setNumber(1);
                dct1.setYear(2020);
                dct1.setSignatory(signatory);
                dct1.setPerformer(performer);
                dct1.setAddressee(addressee);

                em.persist(dct1);

                tx.commit();
                em.close();
        	}
            {
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

                //assertEquals(dcts.size(), 1);
                System.out.println("Number of documents [1]: "+ dcts.size());
                //assertEquals(dcts.get(0).getSignatory().getGivenName(), "John");
                System.out.println("Signatory Given Name [John]: " + dcts.get(0).getSignatory().getGivenName());
                //assertEquals(dcts.get(0).getSignatory().getSurName(), "Green");
                System.out.println("Signatory Sur Name [Green]: " + dcts.get(0).getSignatory().getSurName());
                //assertEquals(dcts.get(0).getPerformer().getGivenName(), "Семен");
                System.out.println("Performer Given Name [Семен]: " + dcts.get(0).getPerformer().getGivenName());
                //assertEquals(dcts.get(0).getPerformer().getSurName(), "Петров");
                System.out.println("Performer Sur Name [Петров]:" + dcts.get(0).getPerformer().getSurName());
                //assertEquals(dcts.get(0).getAddressee().getGivenName(), "Лука");
                System.out.println("Addressee Given Name [Лука]:" + dcts.get(0).getAddressee().getGivenName());
                //assertEquals(dcts.get(0).getAddressee().getSurName(), "Семенов");
                System.out.println("Addressee Sur Name [Семенов]: " + dcts.get(0).getAddressee().getSurName());

                tx.commit();
                em.close();
            }
            {
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

                //assertEquals(dcts.size(), 1);
                System.out.println("Number of documents [1]: "+ dcts.size());

                dcts.get(0).getSignatory().setGivenName("Иван");
                dcts.get(0).getSignatory().setSurName("Зеленский");

                tx.commit();
                em.close();
            }
            {
                UserTransaction tx = TM.getUserTransaction();
                tx.begin();

                EntityManager em = emf.createEntityManager();

                List<Dct> dcts =
                		em.createQuery("select d from Dct d").getResultList();

                //assertEquals(dcts.size(), 1);
                System.out.println("Number of documents [1]: "+ dcts.size());
                //assertEquals(dcts.get(0).getSignatory().getGivenName(), "Иван");
                System.out.println("Signatory Given Name [Иван]: " + dcts.get(0).getSignatory().getGivenName());
                //assertEquals(dcts.get(0).getSignatory().getSurName(), "Зеленский");
                System.out.println("Signatory Sur Name [Зеленский]: " + dcts.get(0).getSignatory().getSurName());
                //assertEquals(dcts.get(0).getPerformer().getGivenName(), "Семен");
                System.out.println("Performer Given Name [Семен]: " + dcts.get(0).getPerformer().getGivenName());
                //assertEquals(dcts.get(0).getPerformer().getSurName(), "Петров");
                System.out.println("Performer Sur Name [Петров]:" + dcts.get(0).getPerformer().getSurName());
                //assertEquals(dcts.get(0).getAddressee().getGivenName(), "Лука");
                System.out.println("Addressee Given Name [Лука]:" + dcts.get(0).getAddressee().getGivenName());
                //assertEquals(dcts.get(0).getAddressee().getSurName(), "Семенов");
                System.out.println("Addressee Sur Name [Семенов]: " + dcts.get(0).getAddressee().getSurName());

                tx.commit();
                em.close();
            }
        } finally {
            TM.rollback();
            emf.close();
        }
    }
}
