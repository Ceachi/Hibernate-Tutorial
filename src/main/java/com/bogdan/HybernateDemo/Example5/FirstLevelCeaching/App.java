package com.bogdan.HybernateDemo.Example5.FirstLevelCeaching;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bogdan.HybernateDemo.Example1.Alien;

/*	TEORIE
 * 
 * CLIENT -->  SERVER (are un cach el)  -----> BD
 * 
 * cand vrei sa faci o interogare (select) pe o sesiune el se duce in BD.
 * ca sa nu faci interogarea aia de foarte multe ori, Hybernate, default
 * iti face un asa numit First Level Caching si iit pune in server interogarea respectiva( ca un fel de buffer),
 * astfel incat cand dai din nou acelasi query, el se uita mai intai in First Level caching sa verifice daca este acolo,
 * daca da, il ia pe acela, => o sa ai 1 singura interogare
 * 
 * De retinut: fiecare sesiune are propria sa First Level Caching, deci daca ai mai multe sesiuni,
 * fiecare are propriul sau First Level Caching.
 * Daca ai mai multe sesiuni, mai exista un asa numit Second Level Caching, care este un cach sharuit intre sesiuni
 * 
 * De exemplu First Level Cach:
 * obj = session1.get(query)
 * ------
 * obj2 = session1.get(query)    // acelasi query
 * 
 * - in cazul asta ( fiind pe aceeiasi sesiune)
 * - prima interogare se uita in First L Cach, vede daca exista interogarea, daca nu exista
 * se duce in Second Level Cach, daca nici acolo nu exista, se duce in Bd, face interogarea si o pune in First Level cach
 * 
 * - la a doua interogare, se uita in First Level Cach, vede interogarea si o executa.
 * 
 * Rezultat =  daca o sa dai, obj si obj2 sunt 2 obiecte separate,  dar query-ul se executa 1 SINGURA DATA PE SESINE
 * 
 * 
 */
public class App {

	//IN ACEST EXEMPLU NE FOLOSIM DE CLASELE DIN PACHETUL EXEMPLU1 PENTRU SIMPLITATE
	public static void main(String[] args) {
		SessionFactory sessionFactory = null;
		Session session1 = null;
		Session session2 = null;
		
		
		
			Alien alien = null;
			
			Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);
			
			ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
			sessionFactory = con.buildSessionFactory(reg);
			
		    session1 = sessionFactory.openSession();
		    
		    session1.beginTransaction();
		    alien = session1.get(Alien.class, 105);
		    System.out.println(alien);
		    
		    alien = session1.get(Alien.class, 105);
		    System.out.println(alien);
		    
		    
		    /*
		     * Rezultat:
		     * Hibernate: select alien0_.aid as aid1_0_0_, alien0_.aname as aname2_0_0_, alien0_.alien_color as alien_co3_0_0_ from alien_table alien0_ where alien0_.aid=?
					Alien [aid=105, aname=R2D2, color=GREEN]
					Alien [aid=105, aname=R2D2, color=GREEN]
					
					se objerva ca s-a efectuat 1 singur query
		     */
			
			session1.getTransaction().commit();
			session1.close();
			
			// mai facem o sesiune 
			session2 = sessionFactory.openSession();
			session2.beginTransaction();
			 
			alien = session2.get(Alien.class, 105);
		    System.out.println(alien);
		    
		    /*
		     * Hibernate: select alien0_.aid as aid1_0_0_, alien0_.aname as aname2_0_0_, alien0_.alien_color as alien_co3_0_0_ from alien_table alien0_ where alien0_.aid=?
				Alien [aid=105, aname=R2D2, color=GREEN]
				Alien [aid=105, aname=R2D2, color=GREEN]
				Hibernate: select alien0_.aid as aid1_0_0_, alien0_.aname as aname2_0_0_, alien0_.alien_color as alien_co3_0_0_ from alien_table alien0_ where alien0_.aid=?
				Alien [aid=105, aname=R2D2, color=GREEN]
		     * 
		     * Se observa ca interogarea s-a efectuat inca o data
		     * 
		     */
			session2.getTransaction().commit();
			session2.close();
			 
			 
	}
}
