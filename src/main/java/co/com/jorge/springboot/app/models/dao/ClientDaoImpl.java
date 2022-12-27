package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClientDaoImpl implements IClientDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return entityManager.createQuery("from Client").getResultList();
    }

    @Override
    public Client findById() {
        return null;
    }

    @Transactional
    @Override
    public void save(Client client) {
        entityManager.persist(client);
    }
}
