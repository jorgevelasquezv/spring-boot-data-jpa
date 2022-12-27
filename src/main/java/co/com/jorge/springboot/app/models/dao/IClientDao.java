package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Client;

import java.util.List;

public interface IClientDao {

    List<Client> findAll();

    Client findById(Long id);

    void save(Client client);

    void delete(Long id);
}
