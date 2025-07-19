package snn.soluciones.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import snn.soluciones.com.auth.handler.LoginSuccessHandler;
import snn.soluciones.com.service.impl.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {

  @Autowired
  private LoginSuccessHandler successHandler;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JpaUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // Configuración de headers - deshabilitando frame options
        .headers(headers -> headers
            .frameOptions(FrameOptionsConfig::disable)
        )
        // Configuración de autorización
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(
                "/css/**",
                "/scss/**",
                "/js/**",
                "/images/**",
                "/vendor/**",
                "/fonts/**",
                "/assets/**",
                "/static/**",
                "/locale/**",
                "/ubicacion/**",
                "/nuevo-registro/**",
                "/account/forgot/**",
                "/error",
                "/error/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        // Configuración del formulario de login
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(successHandler)
            .failureUrl("/login?error")
            .permitAll()
        )
        // Configuración de logout
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        // Manejo de excepciones
        .exceptionHandling(exceptions -> exceptions
            .accessDeniedPage("/error_403")
        );

    return http.build();
  }

  // Configuración para ignorar completamente ciertos paths de Spring Security
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(
            "/css/**",
            "/js/**",
            "/images/**",
            "/vendor/**",
            "/fonts/**",
            "/assets/**",
            "/static/**"
        );
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}