package co.com.jorge.springboot.app.models.service;

import co.com.jorge.springboot.app.models.dao.IClientDao;
import co.com.jorge.springboot.app.models.dao.IInvoiceDao;
import co.com.jorge.springboot.app.models.dao.IProductDao;
import co.com.jorge.springboot.app.models.entities.Client;
import co.com.jorge.springboot.app.models.entities.Invoice;
import co.com.jorge.springboot.app.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClientService{

    @Autowired
    private IClientDao clientDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IInvoiceDao invoiceDao;

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return (List<Client>) clientDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void save(Client client) {
        clientDao.save(client);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clientDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Client fetchByIdWithInvoice(Long id) {
        return clientDao.fetchByIdWithInvoice(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findByName(String term) {

//        return productDao.findByName(term);
        return productDao.findByNameLikeIgnoreCase("%" + term + "%");
    }

    @Transactional
    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Invoice findByInvoiceById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteInvoice(Long id) {
        invoiceDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Invoice fetchInvoiceByIdWithClientWithItemInvoiceWithProduct(Long id) {
        return invoiceDao.fetchByIdWithClientWithItemInvoiceWithProduct(id);
    }

}
