package com.microservicearchitecture.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.database")
public class DbConfig {

    private String db;
    private String host;
    private String username;
    private String password;
    private Integer port;
    private boolean migrationEnabled;

    @PostConstruct
    public void postConstruct() {
        if (db == null || host == null || username == null || password == null || port == null) {
            throw new IllegalStateException("Some of the database configurations not set!");
        }
    }
}
