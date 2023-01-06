package co.com.jorge.springboot.app.config;

import co.com.jorge.springboot.app.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

// https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig{

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> {
                            try {
                                auth
                                                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/list" ).permitAll()
//                                                .requestMatchers("/see/**").hasRole("USER")
//                                                .requestMatchers("/uploads/**").hasRole("USER")
//                                                .requestMatchers("/form/**").hasRole("ADMIN")
//                                                .requestMatchers("/delete/**").hasRole("ADMIN")
//                                                .requestMatchers("/invoice/**").hasRole("ADMIN")
                                                .anyRequest().authenticated()
                                                .and()
                                                .formLogin()
                                                .successHandler(loginSuccessHandler)
                                                .loginPage("/login").permitAll()
                                                .and()
                                                .logout().permitAll()
                                                .and().exceptionHandling().accessDeniedPage("/error_403");
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
//        PasswordEncoder encoder = passwordEncoder();
//        UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//        builder.inMemoryAuthentication()
//                .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
//                .withUser(users.username("jorge").password("12345").roles("USER"));
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");


    }

}
