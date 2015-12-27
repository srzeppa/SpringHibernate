package com.srzeppa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "get.All.Purchases", query = "Select p from Purchase p"),
})
public class Purchase {
	
	public Purchase(double price, String date, String commodity, Client client) {
		super();
		this.price = price;
		this.date = date;
		this.commodity = commodity;
		this.client = client;
	}

	public Purchase() {
		super();
	}

	private int id;
	private double price;
	private String date;
	private String commodity;
	private Client client;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	@ManyToOne//(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
