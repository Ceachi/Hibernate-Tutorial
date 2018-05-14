package com.bogdan.HybernateDemo.Example6.SecondLevelCaching;


import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "extraterestrii_table")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
// ai mai multe optiuni, Read_Only se aplica doar cand vrei sa citesti, 
// e bun atunci cand nu iti dai update la baza de date
public class Extraterestrii {
	@Id
	int aid;
	//@Transient // daca vreau sa nu construiasca/puna coloana asta
	String aname;
	@Column(name = "extraterestru_color") // se poate si fara, dar vreau eu ca coloana sa aibe numele alien_color
	String color;
	
	
	
	
	public Extraterestrii() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Extraterestrii(int aid, String aname, String color) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.color = color;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "Alien [aid=" + aid + ", aname=" + aname + ", color=" + color + "]";
	}
	

}
