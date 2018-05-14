package com.bogdan.HybernateDemo.Example7.HQL;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "human_table")
public class Human {
	@Id
	private int rollno;
	private String name;
	private int marks;
	
	
	
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "Human [rollno=" + rollno + ", name=" + name + ", marks=" + marks + "]";
	}
	
	
		
}
