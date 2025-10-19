package com.example.webapp1a.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@Order(1)
public class RestSecurityConfiguration{

   @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(registry -> {
                registry.antMatchers(HttpMethod.GET,"/api/orders").hasRole("ADMIN");  
                registry.antMatchers(HttpMethod.POST,"/api/orders").hasRole("ADMIN"); 
                registry.anyRequest().permitAll();
            })
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8443")
                .allowCredentials(true);
    }     
}
