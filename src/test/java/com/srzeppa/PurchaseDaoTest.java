package com.srzeppa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.dao.ClientDao;
import com.srzeppa.dao.PurchaseDao;
import com.srzeppa.domain.Client;
import com.srzeppa.domain.Purchase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PurchaseDaoTest {
	
	final static Logger LOGGER = Logger.getLogger(ClientDaoTest.class);
	
	@Autowired
	PurchaseDao purchaseDao;
	@Autowired
	ClientDao clientDao;
	
	Purchase purchase1 = new Purchase();
	Client client1 = new Client();
	
	private final int PRICE_1 = 100;
	private final String DATE_1 = "2000-01-01";
	private final String COMMODITY_1 = "koszulka";
	
	private final String FIRSTNAME_1 = "Bolek";
	private final String LASTNAME_1 = "Lolek";
	private final int PESEL_1 = 213213;
	
	@Before
	public void before(){
		client1.setFirstname(FIRSTNAME_1);
		client1.setLastname(LASTNAME_1);
		client1.setPesel(PESEL_1);
		
		LOGGER.info("-----client set");
		
		purchase1.setPrice(PRICE_1);
		purchase1.setDate(DATE_1);
		purchase1.setCommodity(COMMODITY_1);
		purchase1.setClient(client1);
		
		LOGGER.info("-----purchase set");
		
		clientDao.addClient(client1);
		LOGGER.info("-----client add");
		purchaseDao.addPurchase(purchase1);
		LOGGER.info("-----purchase add");
	}
	
	@After
	public void after(){
		List<Purchase> purchases = purchaseDao.getAllPurchases();
		if(!purchases.isEmpty()){
			for(Purchase p : purchases){
				purchaseDao.deletePurchase(p);
			}
		}
	}
	
	@Test
	public void addPurchaseCheck(){
		List<Client> clients = clientDao.getAllClients();
		Purchase purchase = new Purchase(PRICE_1,DATE_1,COMMODITY_1,clients.get(0));
		purchaseDao.addPurchase(purchase);
		
		Purchase retrievedPurchase = purchaseDao.getPurchaseById(purchase.getId());

		assertEquals(PRICE_1, retrievedPurchase.getPrice());
		assertEquals(DATE_1, retrievedPurchase.getDate());
		assertEquals(COMMODITY_1, retrievedPurchase.getCommodity());
	}

}
