package co.com.jorge.springboot.app.auth.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

public interface IJWTService {

    String create(Authentication auth) throws IOException;

    boolean validate(String token);

    Claims getClaims(String token);

    String getUsername(String token);

    Collection<? extends GrantedAuthority> getAuthorities(String token) throws IOException;

    String resolveToken(String token);
}
