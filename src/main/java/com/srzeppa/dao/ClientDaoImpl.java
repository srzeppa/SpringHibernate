package com.srzeppa.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.domain.Client;
import com.srzeppa.domain.Purchase;

@Component
@Transactional
public class ClientDaoImpl implements ClientDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addClient(Client client) {
		sessionFactory.getCurrentSession().persist(client);
		
	}

	public List<Client> getAllClients() {
		return sessionFactory.getCurrentSession().getNamedQuery("get.All.Clients").list();
	}

	public void deleteClient(Client client) {
		client = (Client) sessionFactory.getCurrentSession().get(Client.class,client.getId());
		
		for (Purchase purchase : client.getPurchase()) {
			sessionFactory.getCurrentSession().update(purchase);
		}
		sessionFactory.getCurrentSession().delete(client);
	}

	public Client getClientById(int id) {
		return (Client) sessionFactory.getCurrentSession().get(Client.class, id);
	}

	public void deleteClientById(int id) {
		sessionFactory.getCurrentSession().delete(id);
		
	}

	public void updateClient(Client client) {
		Client clientToUpdate = getClientById(client.getId());
		clientToUpdate.setFirstname(client.getFirstname());
		clientToUpdate.setLastname(client.getLastname());
		clientToUpdate.setPesel(client.getPesel());
		sessionFactory.getCurrentSession().update(clientToUpdate);
		
	}

}
