package com.example.webapp1a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    
    @Autowired
    UserDetailService userDetailsService; 

    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf().disable()

            

            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/orders/user").hasAnyRole("USER");
                registry.antMatchers("/orders").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/{name}").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/date/{name}").hasAnyRole("ADMIN");

                registry.antMatchers("/orders/users/{id}").hasAnyRole("USER");
                registry.antMatchers("/orders/admin").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/{id}/detail").hasAnyRole("USER");
                registry.antMatchers("/orders/{id}/state").hasAnyRole("ADMIN");
                registry.antMatchers("/orders/{id}/state/update").hasAnyRole("ADMIN");
            })

            .formLogin().disable()
            .logout(logout -> {
                logout.logoutUrl("/logout");
                logout.invalidateHttpSession(true);
                logout.clearAuthentication(true);
                logout.deleteCookies("SESSIONID");
            })

            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
            
        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        http.cors();
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8443")
                .allowCredentials(true);
    }
}
