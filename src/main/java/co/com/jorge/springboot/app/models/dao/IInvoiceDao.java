package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInvoiceDao extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i join fetch i.client c join fetch i.itemInvoices e join fetch e.product p where i.id=?1")
    Invoice fetchByIdWithClientWithItemInvoiceWithProduct(Long id);
}
