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

	@Override
	public void addClient(Client client) {
		sessionFactory.getCurrentSession().save(client);
		
	}

	@Override
	public List<Client> getAllClients() {
		return sessionFactory.getCurrentSession().getNamedQuery("get.All.Clients").list();
	}

	@Override
	public void deleteClient(Client client) {
		client = (Client) sessionFactory.getCurrentSession().get(Client.class,client.getId());
		
		sessionFactory.getCurrentSession().delete(client);
	}

	@Override
	public Client getClientById(int id) {
		return (Client) sessionFactory.getCurrentSession().get(Client.class, id);
	}

	@Override
	public void updateClient(Client client) {
		Client clientToUpdate = getClientById(client.getId());
		clientToUpdate.setFirstname(client.getFirstname());
		clientToUpdate.setLastname(client.getLastname());
		clientToUpdate.setPesel(client.getPesel());
		sessionFactory.getCurrentSession().update(clientToUpdate);
	}
}
