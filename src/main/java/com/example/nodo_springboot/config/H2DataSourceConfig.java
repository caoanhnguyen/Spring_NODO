package com.example.nodo_springboot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class H2DataSourceConfig {

    @Bean
    public DataSource h2DataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1;MODE=MySQL");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setPoolName("Hikari-H2");
        ds.setMaximumPoolSize(5);
        return ds;
    }
}