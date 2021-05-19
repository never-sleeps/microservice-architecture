package com.microservicearchitecture.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

}
