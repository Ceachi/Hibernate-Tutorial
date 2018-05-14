package com.bogdan.HybernateDemo.Example3Relations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bogdan.HybernateDemo.Example2.Alien2;


/*
 * 
 * https://www.youtube.com/watch?v=8mUPvdDGoLQ&index=12&list=PLsyeobzWxl7qBZtsEvp_n2A7sJs2MpF3r
 * 
 * In acest exemplu vorbesc despre cum pot mapa relatiile One to One, One to Many, Many to Many
 * intre clasele Laptop si Student
 * 
 * Exemplu: One to One
 * Laptop --------------------- Student
 * 								@OneToOne
 * 								Laptop laptop
 * (se adauga doar un atribut si o anotare)
 * 
 * Exemplu One to Many
 * Laptop ---------------------- Student
 * @ManyToOne					@OneToMany(mappedBy = "stud")
 * Student stud; 				List<Laptop> laptop
 * 								
 * - daca as fii scris simplu @OnetoMany, ar fii creat un tabel nou numit Student_laptop,
 * ca sa rezolvam, am pus un atribut catre studn din Laptop @OneToMany(mappedBy = "stud")
 * 
 * Exemplu: Many to Many
 * Laptop -------------------------- Student
 * @ManyToMany						@ManyToMany(mapped("stud")
 * private List<Student> stud 		List<Laptop> laptops
 * @ManyToMany face o alta tabela
 * daca nu ai fi scris anotarea asta in abele parti, ci doar la una singura,
 * ar fii creat 2 tabele suplimentare: Laptop_Student si Student_Laptop.
 * Asa cu amandoua , o sa se creeze o noua tabela Laptop_Student
 */
public class App {
	public static void main(String args[]) {
		Laptop laptop = new Laptop();
		laptop.setLid(101);
		laptop.setLname("Dell");
		
		
		Student student = new Student();
		student.setName("Bogdan");
		student.setRollno(1);
		student.setMarks(50);
		//student.setLaptop(laptop);
		student.getLaptop().add(laptop);
		laptop.getStudent().add(student);
		// Hybernate connection
		
		Configuration con = new Configuration().configure()
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Laptop.class);
		
				
		ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sf = con.buildSessionFactory(reg);		
		Session session = sf.openSession();
				
		session.beginTransaction();
		session.save(laptop);
		session.save(student);
		session.getTransaction().commit();
		
		session.close();
		sf.close();
	}
}
