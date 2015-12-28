package com.srzeppa.dao;

import java.util.List;

import com.srzeppa.domain.Client;
import com.srzeppa.domain.Purchase;;

public interface PurchaseDao {
	
	void addPurchase(Purchase purchase);
	List<Purchase> getAllPurchases();
	void deletePurchase(Purchase purchase);
	Purchase getPurchaseById(int id);
	void deletePurchaseById(int id);
	List<Purchase> getAllPurchasesByClient(Client client);
	List<Purchase> getAllPurchasesByClientName(String name);

}
