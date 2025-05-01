package com.absolutecinema.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Added import
import com.absolutecinema.controller.CustomLoginSuccessHandler;
import com.absolutecinema.entity.User;
import com.absolutecinema.repository.UserRepository;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Never store plain text passwords!
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register",
                                "/auth/login",
                                "/auth/forgot-password",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/**" // Allow API access without authentication
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        // .failureHandler((request, response, exception) -> {
                        //     exception.printStackTrace(); // Or log it
                        //     response.sendRedirect("/login?error=true");
                        // })
                        // .defaultSuccessUrl("/auth/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        // Allow GET requests for logout for simplicity, although POST is recommended for CSRF protection.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true) // Default is true
                        .deleteCookies("JSESSIONID") // Default is true
                        .permitAll()
                );

        return http.build();
    }



    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> {
            User user = repo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            List<GrantedAuthority> authorities;
            if ("admin".equalsIgnoreCase(user.getUsername())) {
                authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
            } else {
                authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        };
    }
}