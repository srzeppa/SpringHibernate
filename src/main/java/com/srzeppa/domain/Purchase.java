package com.srzeppa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PURCHASE")
public class Purchase {
	
	public Purchase() {
		
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "DATE")
	private String date;
	
	@Column(name = "COMMODITY")
	private String commodity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}
