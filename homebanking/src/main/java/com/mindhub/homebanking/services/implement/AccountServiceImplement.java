package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {


    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDto> map(List<Account> accounts) {
        return accounts.stream()
                .map(currentAccount -> new AccountDto(currentAccount))
                .collect(Collectors.toList());

    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

}
