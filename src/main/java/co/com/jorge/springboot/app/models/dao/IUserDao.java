package co.com.jorge.springboot.app.models.dao;

import co.com.jorge.springboot.app.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
