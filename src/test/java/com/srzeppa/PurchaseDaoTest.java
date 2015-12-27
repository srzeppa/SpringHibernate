package com.srzeppa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;
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
	
	private final double PRICE_1 = 100.0;
	private final String DATE_1 = "2000-01-01";
	private final String COMMODITY_1 = "koszulka";
	
	@Before
	public void before(){
		purchase1.setPrice(PRICE_1);
		purchase1.setDate(DATE_1);
		purchase1.setCommodity(COMMODITY_1);
		
		List<Client> clients = clientDao.getAllClients();
		purchase1.setClient(clients.get(0));
		
		purchaseDao.addPurchase(purchase1);
		LOGGER.info("purchase1: " + purchase1.getClient().getId());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void addPurchaseCheck(){
		Purchase purchase = new Purchase();
		purchase.setPrice(PRICE_1);
		purchase.setDate(DATE_1);
		purchase.setCommodity(COMMODITY_1);
		
		List<Client> clients = clientDao.getAllClients();
		purchase.setClient(clients.get(0));
		
		LOGGER.info("purchase1222: " + purchase1.getClient().getId());

		purchaseDao.addPurchase(purchase);
		
		Purchase retrievedPurchase = purchaseDao.getPurchaseById(purchase.getId());

		//assertEquals(PRICE_1, retrievedPurchase.getPrice());
//		assertEquals(DATE_1, retrievedPurchase.getDate());
//		assertEquals(COMMODITY_1, retrievedPurchase.getCommodity());
	}

}
