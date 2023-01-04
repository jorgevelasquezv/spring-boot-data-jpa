package co.com.jorge.springboot.app.models.service;

import co.com.jorge.springboot.app.models.entities.Client;
import co.com.jorge.springboot.app.models.entities.Invoice;
import co.com.jorge.springboot.app.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Page<Client> findAll(Pageable pageable);

    Client findById(Long id);

    void save(Client client);

    void delete(Long id);

    List<Product> findByName(String term);

    void saveInvoice(Invoice invoice);

    Product findProductById(Long id);

    Invoice findByInvoiceById(Long id);

    void deleteInvoice(Long id);
}
