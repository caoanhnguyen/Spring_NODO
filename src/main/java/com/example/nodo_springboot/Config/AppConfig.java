package com.example.nodo_springboot.Config;

import com.example.nodo_springboot.entities.Address;
import com.example.nodo_springboot.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Address getAddress() {
        return new Address(1L, "123 Main St", "Springfield", "IL", "62701");
    }

}
