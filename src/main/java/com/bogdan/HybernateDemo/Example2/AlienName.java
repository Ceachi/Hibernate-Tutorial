package com.bogdan.HybernateDemo.Example2;

import javax.persistence.Embeddable;
/*
 * Facand o clasa Embeddable, practic o trimiti ca obiect
 * - daca te uiti in Alien2, ai sa vezi ca el
 * are un obiect declarat ca atribut AlienName
 * 
 */
@Embeddable
public class AlienName {
	String fname;
	String lname;
	String mmname;
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getMmname() {
		return mmname;
	}
	public void setMmname(String mmname) {
		this.mmname = mmname;
	}
	@Override
	public String toString() {
		return "AlienName [fname=" + fname + ", lname=" + lname + ", mmname=" + mmname + "]";
	}
	
	
}
