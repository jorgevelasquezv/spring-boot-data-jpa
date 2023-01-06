package co.com.jorge.springboot.app.models.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = -8661616701938996353L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String authority;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
