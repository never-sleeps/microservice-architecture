package com.microservicearchitecture.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        return "{\"status\": \"OK\"}";
    }

    @GetMapping("/")
    public String sayHello() {
        String hostName;
        try {
            InetAddress myHost = InetAddress.getLocalHost();
            hostName = myHost.getHostName();
        } catch (UnknownHostException e) {
            hostName = "UnknownHost";
        }
        return "Hello from " + hostName;
    }
}
