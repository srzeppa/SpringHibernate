package com.srzeppa.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.srzeppa.domain.Client;

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
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	public Client getClientById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteClientById(int id) {
		// TODO Auto-generated method stub
		
	}

}
