package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<AccountDto> getAccount(){
        List<Account>allAccount = accountRepository.findAll();

        List<AccountDto> acccountDtoCovertList = allAccount
                .stream()
                .map(Account -> new AccountDto(Account))
                .collect(Collectors.toList());
        return acccountDtoCovertList;
    }

    @GetMapping("/accounts/{id}")
    public AccountDto getAccountById(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDto(accountOptional.get());
    }
}
