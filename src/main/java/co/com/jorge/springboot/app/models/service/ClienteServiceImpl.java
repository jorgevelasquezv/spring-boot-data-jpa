package co.com.jorge.springboot.app.models.service;

import co.com.jorge.springboot.app.models.dao.IClientDao;
import co.com.jorge.springboot.app.models.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClientService{

    @Autowired
    private IClientDao clientDao;

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long id) {
        return clientDao.findById(id);
    }

    @Transactional
    @Override
    public void save(Client client) {
        clientDao.save(client);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }
}
