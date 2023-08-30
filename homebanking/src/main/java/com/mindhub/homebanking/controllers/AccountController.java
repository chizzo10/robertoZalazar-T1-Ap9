package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDto> getAccount(){
        List<Account>allAccount = accountRepository.findAll();

        List<AccountDto> acccountDtoCovertList = allAccount
                .stream()
                .map(Account -> new AccountDto(Account))
                .collect(Collectors.toList());
        return acccountDtoCovertList;
    }



    @RequestMapping("/clients/current/accounts")
    public List<AccountDto> getAccount( Authentication authentication) {
        Client client= clientRepository.findByEmail(authentication.getName()) ;
        return client.getAccounts().stream().map(account -> new AccountDto(account)).collect(Collectors.toList());

    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Client client = clientRepository.findByEmail(authentication.getName());

            if (account.getUser().equals(client)) {
                AccountDto accountDto = new AccountDto(account);
                return new ResponseEntity<>(accountDto, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Esta cuenta no es tuya", HttpStatus.I_AM_A_TEAPOT);
            }
        } else {
            return new ResponseEntity<>("Cuenta no encontrada", HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication) {

        String username = authentication.getName();


        Client client = clientRepository.findByEmail(username);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }


        if (client.getAccounts().size() >= 3) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Client already has 3 accounts.");
        }


        String accountNumber = generateAccountNumber();


        Account account = new Account(accountNumber, LocalDate.now(), 0);
        account.setUser(client);
        accountRepository.save(account);

        return ResponseEntity.status(HttpStatus.CREATED).body("New account created for client: " + client.getEmail());
    }


    private String generateAccountNumber() {
        String prefix = "VIN-";
        Random random = new Random();
        int accountNumber = random.nextInt(900000) + 100000;
        return prefix + accountNumber;
    }





}
