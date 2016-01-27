package com.srzeppa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PurchaseDaoTest {

	final static Logger LOGGER = Logger.getLogger(ClientDaoTest.class);

	@Autowired
	PurchaseDao purchaseDao;
	@Autowired
	ClientDao clientDao;

	Purchase purchase1 = new Purchase();
	Client client1 = new Client();
	Purchase purchase2 = new Purchase();
	Client client2 = new Client();

	private final int PRICE_1 = 100;
	private final String DATE_1 = "2000-01-01";
	private final String COMMODITY_1 = "koszulka";

	private final int PRICE_2 = 200;
	private final String DATE_2 = "1999-01-01";
	private final String COMMODITY_2 = "spodnie";

	private final String FIRSTNAME_1 = "Bolek";
	private final String LASTNAME_1 = "Lolek";
	private final int PESEL_1 = 213213;

	private final String FIRSTNAME_2 = "Zdzisiu";
	private final String LASTNAME_2 = "Wiesiu";
	private final int PESEL_2 = 213213;

	@Before
	public void before() {
		client1.setFirstname(FIRSTNAME_1);
		client1.setLastname(LASTNAME_1);
		client1.setPesel(PESEL_1);

		purchase1.setPrice(PRICE_1);
		purchase1.setDate(DATE_1);
		purchase1.setCommodity(COMMODITY_1);
		purchase1.setClient(client1);

		clientDao.addClient(client1);
		purchaseDao.addPurchase(purchase1);

		client2.setFirstname(FIRSTNAME_2);
		client2.setLastname(LASTNAME_2);
		client2.setPesel(PESEL_2);

		purchase2.setPrice(PRICE_2);
		purchase2.setDate(DATE_2);
		purchase2.setCommodity(COMMODITY_2);
		purchase2.setClient(client2);

		clientDao.addClient(client2);
		purchaseDao.addPurchase(purchase2);
	}

	@After
	public void after() {
		List<Client> client = clientDao.getAllClients();
		List<Purchase> purchases = purchaseDao.getAllPurchases();
		if (!purchases.isEmpty()) {
			for (Purchase p : purchases) {
				purchaseDao.deletePurchase(p);
			}
		}
	}

	@Test
	public void addPurchaseCheck() {
		List<Client> clients = clientDao.getAllClients();
		Purchase purchase = new Purchase(PRICE_1, DATE_1, COMMODITY_1, clients.get(0));
		purchaseDao.addPurchase(purchase);

		Purchase retrievedPurchase = purchaseDao.getPurchaseById(purchase.getId());

		assertEquals(PRICE_1, retrievedPurchase.getPrice());
		assertEquals(DATE_1, retrievedPurchase.getDate());
		assertEquals(COMMODITY_1, retrievedPurchase.getCommodity());
	}

	@Test
	public void getPurchaseByIdCheck() {
		Purchase purchaseForTest = new Purchase(PRICE_1, DATE_1, COMMODITY_1, client1);
		Purchase purchaseForTest2 = new Purchase();

		purchaseDao.addPurchase(purchaseForTest);
		purchaseForTest2 = purchaseDao.getPurchaseById(purchaseForTest.getId());

		assertEquals(PRICE_1, purchaseForTest2.getPrice());
		assertEquals(DATE_1, purchaseForTest2.getDate());
		assertEquals(COMMODITY_1, purchaseForTest2.getCommodity());
		assertEquals(client1, purchaseForTest2.getClient());
	}

	@Test
	public void getAllPurchasesCheck() {
		List<Purchase> purchases = purchaseDao.getAllPurchases();
		int purchasesSize = purchases.size();
		assertEquals(purchasesSize, purchases.size());
	}

	@Test
	public void deletePurchaseCheck() {
		List<Purchase> retrievedPurchasesBeforeDelete = purchaseDao.getAllPurchases();
		int sizePurchasesBeforeDelete = retrievedPurchasesBeforeDelete.size();
		purchaseDao.deletePurchase(retrievedPurchasesBeforeDelete.get(0));

		List<Purchase> retrievedPurchasesAfterDelete = purchaseDao.getAllPurchases();
		int sizePurchasesAfterDelete = retrievedPurchasesAfterDelete.size();

		assertNotSame(sizePurchasesAfterDelete, sizePurchasesBeforeDelete);
	}

	@Test
	public void getPurchaseByClientNameCheck() {
		List<Purchase> purchases = purchaseDao.getAllPurchases();
		List<Client> clients = clientDao.getAllClients();

		String pattern = clients.get(0).getLastname();

		int tmp = 0;

		for (int i = 0; i < purchases.size(); i++) {
			/*
			 * if (Pattern.compile(".*" + pattern +
			 * ".*").matcher(purchaseDao.getPurchaseById(i).getClient().
			 * getLastname()) .matches())
			 */
			LOGGER.info("******" + pattern);
			LOGGER.info("******" + purchaseDao.getPurchaseById(purchases.get(i).getId()).getClient().getLastname());
			if (pattern.equals(purchaseDao.getPurchaseById(purchases.get(i).getId()).getClient().getLastname()))
				tmp++;
		}

		List<Purchase> purchasesByName = purchaseDao.getAllPurchasesByClientName(pattern);

		assertEquals(purchasesByName.size(), tmp);
	}

}
