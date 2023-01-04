package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IProductDao extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name like %?1%")
    List<Product> findByName(String term);

    List<Product> findByNameLikeIgnoreCase(String term);
}
