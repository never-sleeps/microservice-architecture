package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateUserRequest;
import com.microservicearchitecture.dto.request.UpdateUserRequest;
import com.microservicearchitecture.dto.response.GetUserResponse;
import com.microservicearchitecture.dto.request.CreateBillingAccountRequest;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.client.BillingClient;
import com.microservicearchitecture.persistence.repository.UserRepository;
import com.microservicearchitecture.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BillingClient billingClient;

    public List<GetUserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(GetUserResponse::new)
                .collect(Collectors.toList());
    }

    public GetUserResponse findUserById(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", id)));
        return new GetUserResponse(user);
    }

    @Transactional
    public GetUserResponse createUser(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user = userRepository.save(user);

        CreateBillingAccountRequest createBillingAccountRequest = CreateBillingAccountRequest.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .build();
        billingClient.createBillingAccount(createBillingAccountRequest);
        return new GetUserResponse(user);
    }

    public GetUserResponse updateUserById(Long id, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", id)));

        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user = userRepository.save(user);
        return new GetUserResponse(user);
    }

    public void deleteUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", id)));

        userRepository.delete(user);
    }
}
