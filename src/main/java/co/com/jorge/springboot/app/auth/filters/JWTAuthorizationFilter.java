package co.com.jorge.springboot.app.auth.filters;

import co.com.jorge.springboot.app.auth.services.IJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static co.com.jorge.springboot.app.auth.services.JWTServicesImpl.*;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private IJWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, IJWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);

        if (!requeridAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }


        UsernamePasswordAuthenticationToken authentication = null;

        if (jwtService.validate(header)){
            authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getAuthorities(header));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    public boolean requeridAuthentication(String header) {
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }
}
