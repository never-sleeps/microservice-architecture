package com.microservicearchitecture.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
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

    @PostConstruct
    public void postConstruct() {
        log.info(String.format(
                "Database: '%s'. Host: '%s'. Username: '%s'. Password: '%s'. Port: '%s'",
                db, host, username, password, port
        ));
    }
}
