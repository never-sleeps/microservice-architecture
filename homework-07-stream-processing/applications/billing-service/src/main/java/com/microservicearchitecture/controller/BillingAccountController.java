package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.request.CreateAccountRequest;
import com.microservicearchitecture.dto.request.DepositMoneyRequest;
import com.microservicearchitecture.dto.request.WithdrawMoneyRequest;
import com.microservicearchitecture.dto.response.CreateAccountResponse;
import com.microservicearchitecture.dto.response.GetAccountResponse;
import com.microservicearchitecture.service.BillingAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class BillingAccountController {

    private final BillingAccountService accountService;

    /**
     * Создание счёта пользователя
     * @param request данные счёта пользователя
     * @return данные счёта пользователя
     */
    @PostMapping("/billing/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        log.info("Создание счета для пользователя с id = " + request.getUserId());
        return accountService.createAccount(request);
    }

    /**
     * Получение счёта пользователя по id пользователя
     * @param userId id пользователя
     * @return данные счёта пользователя
     */
    @GetMapping("/billing/accounts/{userId}")
    public GetAccountResponse findAccountByUserId(@PathVariable Long userId) {
        log.info("Получение данных счёта для пользователя с с id = " + userId);
        return accountService.findAccountByUserId(userId);
    }

    /**
     * Пополнение счёта пользователя
     * @param userId id пользователя
     * @param request данные пополнения
     */
    @PostMapping("/billing/accounts/{userId}/deposit")
    public void depositMoney(
            @PathVariable Long userId,
            @RequestBody DepositMoneyRequest request
    ) {
        log.info("Изменение счёта пользователя с id = " + userId + ": + " + request.getAmount());
        accountService.deposit(userId, request);
    }

    /**
     * Снятие со счёта пользователя
     * @param userId id пользователя
     * @param request данные снятия
     */
    @PostMapping("/billing/accounts/{userId}/withdraw")
    public void withdraw(
            @PathVariable Long userId,
            @RequestBody WithdrawMoneyRequest request
    ) {
        log.info("Изменение счёта пользователя с id = " + userId + ": -" + request.getAmount());
        accountService.withdraw(userId, request);
    }
}
