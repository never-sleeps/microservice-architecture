package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateAccountRequest;
import com.microservicearchitecture.dto.request.DepositMoneyRequest;
import com.microservicearchitecture.dto.request.WithdrawMoneyRequest;
import com.microservicearchitecture.dto.response.CreateAccountResponse;
import com.microservicearchitecture.dto.response.GetAccountResponse;
import com.microservicearchitecture.exceptions.AccountBalanceException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.persistence.entity.Account;
import com.microservicearchitecture.persistence.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
@AllArgsConstructor
public class BillingAccountService {

    private final AccountRepository accountRepository;

    public GetAccountResponse findAccountByUserId(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by userId=" + userId));
        return new GetAccountResponse(account);
    }

    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Account account = Account.builder()
                .userId(request.getUserId())
                .mail(request.getMail())
                .build();
        Account created = accountRepository.save(account);
        return new CreateAccountResponse(created);
    }

    public void deposit(Long userId, DepositMoneyRequest request) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by userId=" + userId));

        BigDecimal balance = account.getBalance();
        account.setBalance(balance.add(request.getAmount()));
        accountRepository.save(account);
    }

    public void withdraw(Long userId, WithdrawMoneyRequest request) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by userId=" + userId));

        BigDecimal balance = account.getBalance();
        if (balance.compareTo(request.getAmount()) < 0) {
            throw new AccountBalanceException(
                    String.format("Account balance for userId=%s: %s < %s", userId, balance, request.getAmount())
            );
        }
        account.setBalance(balance.subtract(request.getAmount()));
        accountRepository.save(account);
    }
}
