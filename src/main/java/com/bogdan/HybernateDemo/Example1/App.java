package com.bogdan.HybernateDemo.Example1;

import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.loader.Loader;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class App {
	public static void main(String args[]) {
		SessionFactory sessionFactory = null;
		Session session = null;
		
		
		
		Alien alien = new Alien();
		alien.setAid(105);
		alien.setAname("R2D2");
		alien.setColor("GREEN");
		
		try {
			// Hybernate connection
			
			Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);
			
			ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
			sessionFactory = con.buildSessionFactory(reg);
			
		    session = sessionFactory.openSession();
			
			// this was just for the connection
			
			
				// TRANSACTION:
			
			Transaction tx = session.beginTransaction();
			session.save(alien); // INSERT	
			/* SELECT BY ID
			Alien obj = new Alien();
			obj = (Alien) session.get(Alien.class, 103);
			System.out.println(obj);
			*/
			tx.commit();
		
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
			
			if(sessionFactory != null) {
				sessionFactory.close();
			}
		}
		
		
		
		
	}
}


/*
 * 
 * - se creaza un obiect Configuration care este configurat cu
 * un fisier hibernate.cfg.xml. Daca il plasam in src/main/resources maven in pue in root-ul fisierului nostru jar
 * - se creaza un obiect Service Registry ce se foloseste de configurarile creaate inainte.
 * - apoi instanta asta, poate fii pasata mai departe catre un Session Factory, folosita pentru 
 * a obtine sesiunea in cadrul careia incarcam sau fetch data.
 * 
 * 
 * 
 * 
 */
