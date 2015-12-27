package com.srzeppa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.dao.ClientDao;
import com.srzeppa.dao.ClientDaoImpl;
import com.srzeppa.domain.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ClientDaoTest{
	
	final static Logger LOGGER = Logger.getLogger(ClientDaoTest.class);
	
	@Autowired
	ClientDao clientDao;
	
	Client client1 = new Client();
	Client client2 = new Client();

	private final String FIRSTNAME_1 = "Bolek";
	private final String LASTNAME_1 = "Lolek";
	private final int PESEL_1 = 213213;
	
	private final String FIRSTNAME_2 = "Zbysiu";
	private final String LASTNAME_2 = "Wodecki";
	private final int PESEL_2 = 67887;
	
	@Before
	public void before(){
		client1.setFirstname(FIRSTNAME_1);
		client1.setLastname(LASTNAME_1);
		client1.setPesel(PESEL_1);
		clientDao.addClient(client1);
		
		client2.setFirstname(FIRSTNAME_2);
		client2.setLastname(LASTNAME_2);
		client2.setPesel(PESEL_2);
		clientDao.addClient(client2);
	}
	
	@Test
	public void addClientCheck() {

		List<Client> retrievedClients = clientDao.getAllClients();

		Client client = new Client();
		client.setFirstname(FIRSTNAME_2);
		client.setLastname(LASTNAME_2);
		client.setPesel(PESEL_2);

		clientDao.addClient(client);
		
		Client retrievedClient = clientDao.getClientById(client.getId());

		assertEquals(FIRSTNAME_2, retrievedClient.getFirstname());
		assertEquals(LASTNAME_2, retrievedClient.getLastname());
		assertEquals(PESEL_2, retrievedClient.getPesel());
	}
	
	@Test
	public void getAllClientsCheck(){
		List<Client> clients = clientDao.getAllClients();
		assertEquals(2, clients.size());
	}
	
	@Test
	public void deleteClientCheck(){
		List<Client> retrievedClients = clientDao.getAllClients();
		
		clientDao.deleteClient(retrievedClients.get(0));
		
		retrievedClients = clientDao.getAllClients();
		assertEquals(1, retrievedClients.size());
	}
	
	@Test
	public void deleteClientByIdCheck(){

	}

}
