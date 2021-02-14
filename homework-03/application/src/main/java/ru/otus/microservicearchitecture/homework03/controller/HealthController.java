package ru.otus.microservicearchitecture.homework03.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@AllArgsConstructor
@RestController
public class HealthController {

    @GetMapping("/")
    public String hello() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "UnknownHost";
        }
        return "Hello from " + hostName;
    }

    @GetMapping("/health")
    public Response healthCheck() {
        return new Response("OK");
    }

    @AllArgsConstructor
    private static class Response{
        private final String status;
    }
}
