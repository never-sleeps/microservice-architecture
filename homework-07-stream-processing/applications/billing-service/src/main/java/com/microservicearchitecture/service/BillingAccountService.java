package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateAccountRequest;
import com.microservicearchitecture.dto.request.DepositMoneyRequest;
import com.microservicearchitecture.dto.request.WithdrawMoneyRequest;
import com.microservicearchitecture.dto.response.CreateAccountResponse;
import com.microservicearchitecture.dto.response.GetAccountResponse;
import com.microservicearchitecture.exceptions.AccountBalanceException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.persistence.entity.BillingAccountEntity;
import com.microservicearchitecture.persistence.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BillingAccountService {

    private final AccountRepository accountRepository;

    public List<GetAccountResponse> findAllAccounts() {
        log.info("Получение списка счетов пользователей");
        return accountRepository.findAll().stream()
                .map(GetAccountResponse::new)
                .collect(Collectors.toList());
    }

    public GetAccountResponse findAccountByUserId(Long userId) {
        log.info("Получение данных счета пользователя userId = " + userId);
        BillingAccountEntity billingAccountEntity = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for userId: " + userId));
        return new GetAccountResponse(billingAccountEntity);
    }

    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        log.info("Создание счета для пользователя userId = " + request.getUserId());
        BillingAccountEntity billingAccountEntity = new BillingAccountEntity();
        billingAccountEntity.setEmail(request.getEmail());
        billingAccountEntity.setUserId(request.getUserId());
        BillingAccountEntity created = accountRepository.save(billingAccountEntity);
        return CreateAccountResponse.builder()
                .id(created.getId())
                .userId(created.getUserId())
                .email(created.getEmail())
                .build();
    }

    @Transactional
    public void deposit(Long userId, DepositMoneyRequest request) {
        BillingAccountEntity billingAccountEntity = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for userId: " + userId));

        BigDecimal balance = billingAccountEntity.getBalance();
        billingAccountEntity.setBalance(balance.add(request.getAmount()));
        accountRepository.save(billingAccountEntity);
        log.info("Пополнение счёта пользователя userId = " + userId + ": +" + request.getAmount());
    }

    @Transactional
    public void withdraw(Long userId, WithdrawMoneyRequest request) {
        BillingAccountEntity billingAccountEntity = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found for userId: " + userId));

        BigDecimal balance = billingAccountEntity.getBalance();
        if (balance.compareTo(request.getAmount()) < 0) {
            throw new AccountBalanceException(
                    String.format("Account balance for userId=%s is lower than: %s", userId, request.getAmount())
            );
        }
        billingAccountEntity.setBalance(balance.subtract(request.getAmount()));
        accountRepository.save(billingAccountEntity);
        log.info("Снятие средств со счёта пользователя userId = " + userId + ": -" + request.getAmount());
    }
}
