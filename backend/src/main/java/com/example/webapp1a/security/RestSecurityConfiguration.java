package com.example.webapp1a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(registry -> {
                registry.antMatchers("/api/**");
                registry.antMatchers(HttpMethod.GET,"/order/api/**").hasRole("ADMIN");  
                registry.antMatchers(HttpMethod.POST,"/order/api/**").hasRole("ADMIN"); 
                registry.anyRequest().permitAll();
            })
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        http.cors();
        return http.build();
    }

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("https://localhost:8442")
                .allowedOrigins("http://localhost:8443")
                .allowCredentials(true);
    }     
}
