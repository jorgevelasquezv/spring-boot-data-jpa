package co.com.jorge.springboot.app.models.service;

import co.com.jorge.springboot.app.models.entities.Client;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Client findById(Long id);

    void save(Client client);

    void delete(Long id);
}
