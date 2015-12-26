package com.srzeppa;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.dao.ClientDao;
import com.srzeppa.dao.ClientDaoImpl;
import com.srzeppa.domain.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ClientDaoTest{
	
	@Autowired
	ClientDao clientDao;

	private final String NAME_1 = "Bolek";
	private final String LASTNAME_1 = "Lolek";
	private final int PESEL_1 = 213213; 
	
	@Test
	public void addClientCheck() {

		List<Client> retrievedClients = clientDao.getAllClients();

		Client client = new Client();
		client.setFirstname(NAME_1);
		client.setLastname(LASTNAME_1);
		client.setPesel(PESEL_1);

		clientDao.addClient(client);
	}

}
