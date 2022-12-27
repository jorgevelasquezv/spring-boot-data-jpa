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

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Transactional
    @Override
    public void save(Client client) {
        if (client.getId() != null && client.getId() > 0){
            entityManager.merge(client);
        }else {
            entityManager.persist(client);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }
}
