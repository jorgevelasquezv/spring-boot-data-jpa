package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClientDao extends JpaRepository<Client, Long> {

    @Query("select c from Client c left join fetch c.invoices i where c.id=?1")
    Client fetchByIdWithInvoice(Long id);

}
