package com.bogdan.HybernateDemo.Example2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



/*
 * Exemplu in care clasa Alien2 o sa aibe ca si coloana un obiect
 * AlienName cu atribute specifice.
 * 
 * tabela alien2, o sa contina atributele preluate din obiectul AlienName
 * 
 * ca sa functioneze, asupra lui AlienName trebuie marcat cu anotarea @Embeddable
 * 
 */
public class App {

	public static void main(String[] args) {
		AlienName alienName = new AlienName();
		alienName.setFname("Ceachi");
		alienName.setLname("Bogdan");
		alienName.setMmname("MidleName");
		
		Alien2 alien = new Alien2();
		alien.setAid(106);
		alien.setAname(alienName);
		alien.setColor("GREEN");
		
		
		// Hybernate connection
		
		Configuration con = new Configuration().configure().addAnnotatedClass(Alien2.class);
		
		ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);
		
		Session session = sf.openSession();
		
		// this was just for the connection
		Transaction tx = session.beginTransaction();
		session.save(alien); // add my alien object to the db
		tx.commit();
		
		
		session.close();
		sf.close();
		
		

	}

}
