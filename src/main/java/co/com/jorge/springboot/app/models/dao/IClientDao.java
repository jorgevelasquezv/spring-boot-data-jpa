package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {

}
