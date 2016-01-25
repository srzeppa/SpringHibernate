package com.srzeppa.dao;

import com.srzeppa.domain.Client;
import java.util.List;

public interface ClientDao {

	void addClient(Client client);
	List<Client> getAllClients();
	void deleteClient(Client client);
	Client getClientById(int id);
	void updateClient(Client client);

}
