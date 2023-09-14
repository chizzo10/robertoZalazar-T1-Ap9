package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.dtos.ClientsDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public ResponseEntity<Object> getAccounts() {
        List<Account> accounts = accountService.findAll();
        List<AccountDto> accountsDTO = accountService.map(accounts);
        return new ResponseEntity<>(accountsDTO, HttpStatus.ACCEPTED);
    }


    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountsById(@PathVariable Long id, Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (account == null) {
            return new ResponseEntity<>("account not found", HttpStatus.BAD_GATEWAY);
        }
        if (account.getUser().equals(client)) {
            AccountDto accountDTO = new AccountDto(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("This account it's not your", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts().size() == 3) {
            return new ResponseEntity<>("Max amount of accounts reached", HttpStatus.FORBIDDEN);
        }

        String randomNum = AccountUtils.generateRandomAccountNumber();

        if (accountService.findByNumber(randomNum) != null) {

        }

        Account account = new Account(randomNum, LocalDate.now(), 0.0);
        client.addAccount(account);
        accountService.save(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current/accounts")
    public ResponseEntity<Object> getAccount(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client != null) {
            return new ResponseEntity<>(new ClientsDto(client).getAccounts(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Resource bot found", HttpStatus.NOT_FOUND);
        }
    }
}



