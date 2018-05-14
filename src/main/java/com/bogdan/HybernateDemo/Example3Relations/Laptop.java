package com.bogdan.HybernateDemo.Example3Relations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "laptop")
public class Laptop {

	@Id
	private int lid; // id
	private String lname;
	
//	@ManyToOne
//	private Student student;
//	
	@ManyToMany
	private List<Student> student = new ArrayList<Student>();
	
	
	
	
public List<Student> getStudent() {
		return student;
	}
	public void setStudent(List<Student> student) {
		this.student = student;
	}
	//	public Student getStudent() {
//		return student;
//	}
//	public void setStudent(Student student) {
//		this.student = student;
//	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	@Override
	public String toString() {
		return "Student [lid=" + lid + ", lname=" + lname + "]";
	}
}
