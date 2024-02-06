package com.questionpro.assesment.security;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String ROLE_USER = "USER";
  private static final String ROLE_ADMIN = "ADMIN";

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .headers(hc -> hc.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/v1/**").hasRole(ROLE_USER)
        .requestMatchers("/admin/**").hasRole(ROLE_ADMIN)
        .anyRequest().permitAll()
      )
      .httpBasic(Customizer.withDefaults())
      .formLogin(AbstractHttpConfigurer::disable);
    return http.build();
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    // Creating a default admin user
//    UserDetails adminUserDetails = User.withDefaultPasswordEncoder()
//      .username("admin")
//      .password("admin")
//      .roles("ADMIN")
//      .build();
//
//    // Creating a default user
//    UserDetails userDetails = User.withDefaultPasswordEncoder()
//      .username("user")
//      .password("pass")
//      .roles("USER")
//      .build();
//
//    return new InMemoryUserDetailsManager(adminUserDetails, userDetails);
//  }

  @Bean
  DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
      .setType(H2)
      .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
      .build();
  }

  @Bean
  UserDetailsManager users(DataSource dataSource) {
    UserDetails user = User.builder()
      .username("user")
      .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
      .roles(ROLE_USER)
      .build();
    UserDetails admin = User.builder()
      .username("admin")
      .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
      .roles(ROLE_ADMIN)
      .build();
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.createUser(user);
    users.createUser(admin);
    return users;
  }

  @Bean
  static RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
    return hierarchy;
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return web -> web.debug(true);
//  }

}
