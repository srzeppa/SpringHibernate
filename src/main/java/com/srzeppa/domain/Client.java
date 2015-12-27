package com.srzeppa.domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "get.All.Clients", query = "Select p from Client p"),
})
public class Client {

	private int id;
	private String firstname;
	private String lastname;
	private int pesel;
	private List<Purchase> purchase = new ArrayList<Purchase>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getPesel() {
		return pesel;
	}

	public void setPesel(int pesel) {
		this.pesel = pesel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Purchase> getPurchase() {
		return purchase;
	}

	public void setPurchase(List<Purchase> purchase) {
		this.purchase = purchase;
	}

}
