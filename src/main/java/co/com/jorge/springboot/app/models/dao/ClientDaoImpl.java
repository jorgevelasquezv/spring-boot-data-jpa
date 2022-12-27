package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl implements IClientDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> findAll() {
        return entityManager.createQuery("from Client").getResultList();
    }

    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0){
            entityManager.merge(client);
        }else {
            entityManager.persist(client);
        }
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }
}
