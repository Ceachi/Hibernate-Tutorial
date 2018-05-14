package com.bogdan.HybernateDemo.Example7.HQL;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bogdan.HybernateDemo.Example1.Alien;
import com.bogdan.HybernateDemo.Example6.SecondLevelCaching.Extraterestrii;



/*HQL =  este SQL dar pentru Hibernate
 * 
 * JDBC:
 * ResultSet rs = st.executeQuery(sql)
 * while(rs.next) { ...... }
 * 
 * in Hibernate arata la modul:
 * session.createQuery("from Human"); Unde Human trb sa fie numele clasei, nu a tabelei din BD
 * List<Student> st = query.list();
 * st.forEach(........)
 * 
 *poti face:
 *1)  "from Human"
 *2) from Human where marks > 50
 *3) rom Human where rollno=7  ( vezi in exemple)
 *4) NU POTI FACE: 
 *select *
 *sau
 *select rollno, name, marks from Human where ceva
 *pt ca el se asteapta la un obiect !(vezi in exemplu cum poti face altfel)
 *asta iti returneaza 3 obiecte, pt rollno, name si marks,
 *asa ca pt a le prinde avem nevoie de un array Object[]
 *
 * 
 * 
 * 
 */
public class App {

	
	public static void main(String args[]) {
		SessionFactory sessionFactory = null;
		Session session = null;
		
		
		Configuration con = new Configuration().configure().addAnnotatedClass(Human.class);	
		ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		sessionFactory = con.buildSessionFactory(reg);
		
		
		session = sessionFactory.openSession();	
	    session.beginTransaction();
	   
	    // Populam tabela
	    Random random = new Random();
	    for(int i = 0; i < 50; i++) {
	    	Human human = new Human();
	    	human.setRollno(i);
	    	human.setName("Name" + i);
	    	human.setMarks(random.nextInt(100));
	    	session.save(human);
	    }
	    
	    // vrem sa facem un select * from human_table
	    @SuppressWarnings("unchecked") // asta incercam eu ca sa nu le mai fac depricated
	    Query query = session.createQuery("from Human");// nu este table name, este Entity Name
	    List<Human> humans = query.list(); // metoda pentru a da fetch la valori, intoarce o lista
	    
	    for(Human h : humans) {
	    	System.out.println(h);
	    }
	    
	    // select humans cu makrs >  50
	    System.out.println("Afisam toti humans cu marks > 50");
	    query = session.createQuery("from Human where marks > 50");
	    humans = query.list(); // metoda pentru a da fetch la valori, intoarce o lista
	    
	    for(Human h : humans) {
	    	System.out.println(h);
	    }
	    
	    // vrem sa selectam o singura linie
	    System.out.println("Selectam 1 singura linie");
	    query = session.createQuery("from Human where rollno=7");
	    Human human = (Human) query.uniqueResult();// getting an unique value
	    System.out.println(human);
	    
	    // alt exemplu cu select
	    System.out.println("Alt exemplu de query select rollno, name, marks from Human where rollno = 7");
	    query = session.createQuery("select rollno, name, marks from Human where rollno = 7");
	    Object[] humansArray = (Object[]) query.uniqueResult(); // pt ca intaorce 3 obiecte, rollno, name si marks
	    
	    System.out.println(humansArray[0] + " : " + humansArray[1] + ": " + humansArray[2]);
	    
	    // acelasi exemplu dar fara clauza where
	    
	    System.out.println("Alt exemplu de query select rollno, name, marks from Human");
	    query = session.createQuery("select rollno, name, marks from Human");
	    List<Object[]> humansList = query.list(); // pt ca intaorce 3 obiecte, rollno, name si marks
	    
	    for(Object[] h : humansList) {
	    	  System.out.println(h[0] + " : " + h[1] + ": " + h[2]);
	    }
	  
	    
	    // Pentru Joinuri
	    
	    
	    // acelasi exemplu dar fara clauza where
	    
	    System.out.println("Alt exemplu de query select rollno, name, marks from Human h where h.marks > 60");
	    query = session.createQuery("select rollno, name, marks from Human h  where h.marks > 60");
	    List<Object[]> humansList2 = query.list(); // pt ca intaorce 3 obiecte, rollno, name si marks
	    
	    for(Object[] h : humansList2) {
	    	  System.out.println(h[0] + " : " + h[1] + ": " + h[2]);
	    }
	  
	    // alt exemplu pentru suma
	    System.out.println("Alt exemplu de query elect sum(marks) from Human h  where h.marks > 60");
	    query = session.createQuery("select sum(marks) from Human h  where h.marks > 60");
	    Object marks = query.uniqueResult(); // pt ca intaorce 3 obiecte, rollno, name si marks
	   // sau in loc de Object poti pune Long, Integer(desi poate da eraore, daca returneaza Long
	    System.out.println(marks);
	    
	    
	    // alt exemplu folosim un fel de PreparedStatement ca in JDBC
	    System.out.println("Folosim un query asemanator cu PreparedStament in JDBC");
	    int val = 60;
	    query = session.createQuery("select sum(marks) from Human h  where h.marks >:b"); // se observa doua puncte in fata lu b
	    query.setParameter("b", val);
	    Object marksQuery2 = query.uniqueResult();
	    System.out.println(marksQuery2);
	    
	    
	    
	    
	    
	    
	    //DACA VREM SA FOLOSIM SQL IN LOC DE HQL ( se mai numeste si Native Query)
	    
	    System.out.printf("%n%n%n Using SQL not HQL%n");
	    SQLQuery query2 = session.createSQLQuery("select * from human_table where marks > 60");
	    query2.addEntity(Human.class);// specific ce fel de obiect preiau
	    List<Human> humans5 = query2.list();
	    
	    for(Human h : humans5) {
	    	System.out.println(h);
	    }
	    
	    
	    // alt query
	    System.out.printf("%n%n Inca un query fara HQL ci doar cu SQL");
	    SQLQuery query3 = session.createSQLQuery("select name, marks from human_table where marks > 60");
	    query3.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	    // this will convert your output into map format
	    
	    List human6 = query3.list();
	    
	    for(Object o : human6) {
	    	Map m = (Map) o;
	    	System.out.println(m.get("name") + " : " + m.get("marks") );
	    }
	    
	    
	    
	    
	    
	    session.getTransaction().commit();
	    session.close();    
		
	}
}
