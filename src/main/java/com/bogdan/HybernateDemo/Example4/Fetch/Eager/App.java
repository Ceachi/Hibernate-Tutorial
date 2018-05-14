package com.bogdan.HybernateDemo.Example4.Fetch.Eager;

import java.util.Collection;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bogdan.HybernateDemo.Example3Relations.Laptop;
import com.bogdan.HybernateDemo.Example3Relations.Student;

/*Idea de baza in acest exemplu: Fetch si Eager
 * (amandoua se refera cand vreau sa preiau date dintr-un BD)
 * fetch = este acel angajat care primeste un task, dar intra il face abia in ultima zi din deadline
 * eager = este acel angajat care cum a primit un task, il si executa
 * 
 * Scenariu: 
 * Avem 2 tabele Employee si Computer (care sunt OneToMany), un student are mai multe calculatoare
 * 
 * Fetch:
 * - cand faci Fetch de exemplu sa iei toti studentii, iti ia, dar nu iti da si lista de calculatoare,
 * pentru asta ar trebui sa mai faci un query dupa ca sa ii ieri  ===> faci 2 queriuri in total
 * 
 * Eager:
 * pe clasa Student, in dreptul listei de calculatoare la anotarea @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
 * - observi ca am pus inca un atribut, practic specifici in mod special sa iti ia in primul query
 * si lista de calculatoare ====> 1 singur query
 * 
 */



// TO DO:  IN ACEST EXEMPLU NU MI-A FUNCTIONAT EAGER
// https://www.youtube.com/watch?v=ucuVbL-tsUY&index=13&list=PLsyeobzWxl7qBZtsEvp_n2A7sJs2MpF3r

public class App {
	
	public static void main(String args[]) {
		
		Computer computer = new Computer();
		Employee employee = new Employee();
		
		computer.setCid(101);
		computer.setName("Dell");
		computer.setEmployee(employee);
		
		employee.setSid(1);
		employee.setName("Bogdan");
		
		employee.getComputers().add(computer);
		
		
		Configuration con = new Configuration().configure()
				.addAnnotatedClass(Employee.class)
				.addAnnotatedClass(Computer.class);
		
		
		ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
		SessionFactory sessionFactory = con.buildSessionFactory(reg);		
		Session session = sessionFactory.openSession();
		
		System.out.println("INTRAM AICI");
		session.beginTransaction();
		// add some data
		session.save(computer);
		session.save(employee);
		
		
		/*
		 * In cazul in care dai FETCH simplu queriul:
		 * Employee emp = session.get(Employee.class, 1);
		 * System.out.println(stud);
		 * 
		 * arata ceva de genul:
		 * select employee_.sid, employee_.name as name2_1_ from employee employee_ where employee_.sid=?
		 * 
		 * vezi semnul intrebari ?  inseamna ca trebuie sa mai faci un fel de query ca sa iei lista de calculatoare ale unui employee
		 * 
		 * List<Computer> computers = emp.getComputers();
				for (Computer obj: computers) {
					System.out.println(obj);
				};
				
				
				VEZI CA AICI AM AVUT O EROARE DE STACK OVERFLOW, PT CA CAND AFISAM, ATAT COMPUTER CAT SI EMPLOYEE
				AVEAU TOsTRING AUTOMAT IMPLEMENTAT, SI SE AFISAU UNA PE ALTA, INTRAND INTR-UN FEL DE LOOP
				
			In cazul 	
				
		 */
		
		// In cazul in care vrei sa faci EAGER
		// pui anotarea @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)  la Employee
		// asta o sa iti faca selectul cu tot cu lista in interior ===> 1 singur query
		
		Employee emp = session.get(Employee.class, 1);
		System.out.println(emp.getName());
//		List<Computer> computers = emp.getComputers();
//		for (Computer obj: computers) {
//			System.out.println(obj);
//		};
		
		//System.out.println(emp);
		
		session.getTransaction().commit();
		System.out.println("aM DAT COMIT");
		session.close();
		sessionFactory.close();
		
		
		
	}

}
