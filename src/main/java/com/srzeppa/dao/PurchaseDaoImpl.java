package com.srzeppa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.domain.Client;
import com.srzeppa.domain.Purchase;

@Component
@Transactional
public class PurchaseDaoImpl implements PurchaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addPurchase(Purchase purchase) {
        sessionFactory.getCurrentSession().save(purchase);
        Client client = (Client) sessionFactory.getCurrentSession().get(Client.class, purchase.getClient().getId());
        client.getPurchase().add(purchase);
	}

	@Override
	public List<Purchase> getAllPurchases() {
		return sessionFactory.getCurrentSession().getNamedQuery("get.All.Purchases").list();
	}

	@Override
	public void deletePurchase(Purchase purchase) {
		purchase = (Purchase) sessionFactory.getCurrentSession().get(Purchase.class, purchase.getId());
        Client c = (Client) sessionFactory.getCurrentSession().get(Client.class, purchase.getClient().getId());
        c.getPurchase().remove(purchase);
        sessionFactory.getCurrentSession().delete(purchase);
	}
	
	@Override
	public Purchase getPurchaseById(int id) {
		return (Purchase) sessionFactory.getCurrentSession().get(Purchase.class, id);
	}

	@Override
	public List<Purchase> getAllPurchasesByClient(Client client) {
		List<Purchase> purchases = getAllPurchases();
        List<Purchase> p = new ArrayList<Purchase>();
        for (Purchase purch : purchases)
            if(purch.getClient().getId() == client.getId())
                p.add(purch);
        return p;
	}

	@Override
	public List<Purchase> getAllPurchasesByClientName(String name) {
		List<Purchase> purchase = new ArrayList<Purchase>();
		Pattern pattern = Pattern.compile(".*" + name + ".*");
		Matcher matcher;
		for (Purchase p : getAllPurchases()) {
			matcher = pattern.matcher(p.getClient().getLastname());
			if (matcher.matches())
				purchase.add(p);
		}
		return purchase;
	}

	@Override
	public void updatePurchase(Purchase purchase, Client client, String commodity, String date, int price) {
        purchase = (Purchase) sessionFactory.getCurrentSession().get(Purchase.class, purchase.getId());
        Client c = (Client) sessionFactory.getCurrentSession().get(Client.class, purchase.getClient().getId());
        int i = 0;
        for(Purchase p : c.getPurchase()) {
            if (p == purchase)
                break;
            i++;
        }
        
        purchase.setCommodity(commodity);
        purchase.setDate(date);
        purchase.setPrice(price);
        purchase.setClient(client);
        
        sessionFactory.getCurrentSession().update(purchase);
	}

}
