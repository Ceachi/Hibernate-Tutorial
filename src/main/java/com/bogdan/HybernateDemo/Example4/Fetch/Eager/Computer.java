package com.bogdan.HybernateDemo.Example4.Fetch.Eager;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "computer")
public class Computer {
	
	@Id
	private int cid;
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER)
	Employee employee;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "computer " + cid + " name = " + name + " employee = " + employee;
		//return "Computer [cid=" + cid + ", name=" + name + ", employee=" + employee + "]";
	}

	

	
	

}
