package com.srzeppa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.util.List;

import org.apache.log4j.Logger;
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
	
	private final String FIRSTNAME_3 = "Updated";
	private final String LASTNAME_3 = "Updated";
	private final int PESEL_3 = 999;
	
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
	
	@After
	public void after(){
		List<Client> clients = clientDao.getAllClients();
		if(!clients.isEmpty()){
			for(Client c : clients){
				clientDao.deleteClient(c);
			}
		}
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
	public void updateClientCheck(){
		String firstname, firstnameAfterUpdate;
		String lastname, lastnameAfterUpdate;
		int pesel, peselAfterUpdate;
		
		List<Client> clientBeforeUpdate = clientDao.getAllClients();
		firstname = clientBeforeUpdate.get(0).getFirstname();
		lastname = clientBeforeUpdate.get(0).getLastname();
		pesel = clientBeforeUpdate.get(0).getPesel();
		
		Client clientForUpdate = new Client(FIRSTNAME_3,LASTNAME_3,PESEL_3);
		clientForUpdate.setId(clientBeforeUpdate.get(0).getId());
		clientDao.updateClient(clientForUpdate);
		
		List<Client> clientAfterUpdate = clientDao.getAllClients();
		firstnameAfterUpdate = clientAfterUpdate.get(0).getFirstname();
		lastnameAfterUpdate = clientAfterUpdate.get(0).getLastname();
		peselAfterUpdate = clientAfterUpdate.get(0).getPesel();
		
		assertNotSame(firstname, firstnameAfterUpdate);
		assertNotSame(lastname, lastnameAfterUpdate);
		assertNotSame(pesel, peselAfterUpdate);
	}
	
	@Test
	public void getClientByIdCheck(){
		Client clientForTest = new Client(FIRSTNAME_3, LASTNAME_3, PESEL_3);
		Client clientForTest2 = new Client();
		
		clientDao.addClient(clientForTest);
		clientForTest2 = clientDao.getClientById(clientForTest.getId());
		
		assertEquals(FIRSTNAME_3,clientForTest2.getFirstname());
		assertEquals(LASTNAME_3, clientForTest2.getLastname());
		assertEquals(PESEL_3, clientForTest2.getPesel());
	}

}
