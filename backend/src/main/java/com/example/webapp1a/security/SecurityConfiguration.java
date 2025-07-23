package com.example.webapp1a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    
    @Autowired
    UserDetailService userDetailsService;    

    @Bean
    public UserDetailService userDetailsService(){
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception{
            return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()

            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/orders/users/{id}").hasAnyRole("USER");
                registry.antMatchers("/orders/admin").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/{id}").hasAnyRole("USER");
                registry.antMatchers("/orders/{id}/state").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/{id}/state/update").hasAnyRole("ADMIN");
            });
            
        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
            
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8443")
                .allowCredentials(true);
    }
}
