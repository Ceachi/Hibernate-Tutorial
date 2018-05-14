package com.bogdan.HybernateDemo.Example6.SecondLevelCaching;
import javax.persistence.Cacheable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/*
 * IN ACEST EXEMPLU VREM SA FACEM SECOND LEVEL CACHING
 * - adica daca fac 2 sesiuni, si amandoua au facut aceeiasi interogare
 * - sa aibe un cach comun (second level cach) in care sa se uite, astfel incat interogarea sa se realizeze 1 singura data
 * 
 * Pasii de urmat:
 * 1) Se pun dependentele pentru:
 <groupId>net.sf.ehcache</groupId>
		<artifactId>ehcache</artifactId>
 * 
 * si
 * <groupId>org.hibernate</groupId>
		<artifactId>hibernate-ehcache</artifactId>
		
	vezi ca dependenta hibernate-ehcache sa aibe
	aceeasi versiune cu hibernate-core (dependency)
	daca nu are o iei pe aia
	
	2) Trebuie sa ii specificam in hibernate.cfg.xml ca folosim second level caching
	 
        <property name="hibernate.cache.use_second_level_cache">true</property>
<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>

	3) Asupra clasei tale trb sa ii specific ca este Cache cu anotarea
	@Cacheable
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	acolo ai mai multe functii, read_only este doar sa ii specifici ca vrei pentru read
 * 
 * 
 * Vreau sa mai obervi in main ca am facut un try->catch un un :
 * session1.getTransaction().rollback()
 * ca sa pot face mai multe tranzactii
 * 
 * 
 */
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bogdan.HybernateDemo.Example1.Alien;
public class App {
	public static void main(String[] args) {
		SessionFactory sessionFactory = null;
		Session session1 = null;
		Session session2 = null;
		Session session3 = null;
		
		
		try {
			Extraterestrii alien = null;
			
			Configuration con = new Configuration().configure().addAnnotatedClass(Extraterestrii.class);
			
			ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
			sessionFactory = con.buildSessionFactory(reg);
			
			// session 1 insert values
//		    session1 = sessionFactory.openSession();
//		    session1.beginTransaction();
//		    session1.save(new Extraterestrii(106,"mihai", "red"));
//		    session1.getTransaction().commit();
//		    session1.close();
		    // session 1 insert values close
		    
		    // session1 get some values
		    session1 = sessionFactory.openSession();	
		    session1.beginTransaction();
		    alien = session1.get(Extraterestrii.class, 105);
		    System.out.println("prima interogarea cu sesiunea 1:");
		    System.out.println(alien);
		    session1.getTransaction().commit();
		    session1.close();    
		    // sesion1  get some values  close
		    
		    //session2 get some values  
			session2 = sessionFactory.openSession();
			session2.beginTransaction();
			 
			alien = session2.get(Extraterestrii.class, 105);
		    System.out.println("a doua  interogare cu sesiunea 2:");
		    System.out.println(alien);
			session2.getTransaction().commit();
			session2.close();
			 // session 2 close
			
			
			
			
			 //session3 get some values  PENTRU QUERY NU AM REUSIT SECOND LEVEL CACHING
			// sa te mai uiti la telusko
			//https://www.youtube.com/watch?v=2hYtMfQ2TnQ&list=PLsyeobzWxl7qBZtsEvp_n2A7sJs2MpF3r&index=17
			
			session1 = sessionFactory.openSession();
			session1.beginTransaction();
			Extraterestrii alien2 = null;
			Query q1 = session1.createQuery("from Extraterestrii where aid =105"); // nu exista tabela, trb pus numele clasei
			q1.setCacheable(true);
			 
			alien2 = (Extraterestrii) q1.uniqueResult();
		    System.out.println("a doua  interogare cu sesiunea 3:");
		    System.out.println(alien2);
			session1.getTransaction().commit();
			session1.close();
			 // session 3 close
			
			
		}catch(HibernateException exception){
		     System.out.println("Problem creating session factory");
		     exception.printStackTrace();
		}catch(Exception e) {
			if(session1 != null && session1.getTransaction() != null) {
				session1.getTransaction().rollback();
			}
		}
	}
}
