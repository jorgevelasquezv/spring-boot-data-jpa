package co.com.jorge.springboot.app.config;

import co.com.jorge.springboot.app.auth.filters.JWTAuthenticationFilter;
import co.com.jorge.springboot.app.auth.filters.JWTAuthorizationFilter;
import co.com.jorge.springboot.app.auth.handler.LoginSuccessHandler;
import co.com.jorge.springboot.app.auth.services.IJWTService;
import co.com.jorge.springboot.app.models.service.JpaUserDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

// https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig{

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JpaUserDetailServices userDetailServices;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private IJWTService jwtService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> {
                            try {
                                auth
                                                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/list", "/list-rest", "/locale" ).permitAll()
                                                .anyRequest().authenticated()
//                                                .and()
//                                                .formLogin()
//                                                .successHandler(loginSuccessHandler)
//                                                .loginPage("/login").permitAll()
//                                                .and()
//                                                .logout().permitAll()
//                                                .and().exceptionHandling().accessDeniedPage("/error_403")
                                                .and()
                                                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                                                .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                                                .csrf().disable()
                                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailServices)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
