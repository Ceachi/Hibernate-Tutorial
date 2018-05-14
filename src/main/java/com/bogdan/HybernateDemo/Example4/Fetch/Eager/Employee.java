package com.bogdan.HybernateDemo.Example4.Fetch.Eager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	private int sid;
	private String name;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
	List<Computer> computers = new ArrayList<Computer>();

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", name=" + name;
	}

	
	
	
	
	

}
