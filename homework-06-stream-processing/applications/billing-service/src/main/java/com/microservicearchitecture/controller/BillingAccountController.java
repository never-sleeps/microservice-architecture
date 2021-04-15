package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.request.CreateAccountRequest;
import com.microservicearchitecture.dto.request.DepositMoneyRequest;
import com.microservicearchitecture.dto.request.WithdrawMoneyRequest;
import com.microservicearchitecture.dto.response.CreateAccountResponse;
import com.microservicearchitecture.dto.response.GetAccountResponse;
import com.microservicearchitecture.service.BillingAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/billing/accounts")
public class BillingAccountController {

    private final BillingAccountService accountService;

    /**
     * Создание счёта пользователя
     * @param request данные счёта пользователя
     * @return данные созданного счёта
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return accountService.createAccount(request);
    }

    /**
     * Получение списка всех счетов
     * @return список всех счетов
     */
    @GetMapping
    public List<GetAccountResponse> findAllAccounts() {
        return accountService.findAllAccounts();
    }

    /**
     * Получение данных счёта по id пользователя
     * @param userId id пользователя
     * @return данные счета
     */
    @GetMapping("/{userId}")
    public GetAccountResponse findAccountByUserId(
            @PathVariable Long userId
    ) {
        return accountService.findAccountByUserId(userId);
    }

    /**
     * Пополнение счёта
     * @param userId id пользователя
     * @param request данные пополнения
     */
    @PostMapping("/{userId}/deposit")
    public void depositMoney(
            @PathVariable Long userId,
            @Valid @RequestBody DepositMoneyRequest request
    ) {
        accountService.deposit(userId, request);
    }

    /**
     * Снятие со счёта
     * @param id id пользователя
     * @param request данные снятия
     */
    @PostMapping("/{id}/withdraw")
    public void withdraw(
            @PathVariable Long id,
            @Valid @RequestBody WithdrawMoneyRequest request
    ) {
        accountService.withdraw(id, request);
    }
}
