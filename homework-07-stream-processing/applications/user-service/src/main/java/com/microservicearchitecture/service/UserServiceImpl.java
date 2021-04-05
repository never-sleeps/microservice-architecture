package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.UserRequestDto;
import com.microservicearchitecture.dto.response.UserResponseDto;
import com.microservicearchitecture.dto.request.CreateBillingAccountRequest;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.client.BillingClient;
import com.microservicearchitecture.persistence.UserRepository;
import com.microservicearchitecture.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BillingClient billingClient;

    @Override
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", id)));
        return new UserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setMail(request.getMail());

        User created = userRepository.save(user);

        CreateBillingAccountRequest createBillingAccountRequest = CreateBillingAccountRequest.builder()
                .userId(created.getId())
                .mail(created.getMail())
                .build();
        billingClient.createBillingAccount(createBillingAccountRequest);

        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUserById(Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id =%d not found", id)));

        user.setName(request.getName());
        user.setMail(request.getMail());
        return new UserResponseDto(userRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id =%d not found", id)));

        userRepository.delete(user);
    }
}
