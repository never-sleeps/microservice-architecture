package ru.microservicearchitecture.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.microservicearchitecture.service.SecurityService;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String encodePassword(String password) {
//        password = password + "salt";
//        return Base64.getEncoder().encodeToString(password.getBytes());
        return password;
    }
}
