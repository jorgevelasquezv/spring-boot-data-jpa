package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceDao extends JpaRepository<Invoice, Long> {
}
