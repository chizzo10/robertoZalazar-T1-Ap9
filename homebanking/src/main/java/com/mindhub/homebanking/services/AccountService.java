package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDto;
import com.mindhub.homebanking.models.Account;


import java.util.List;

public interface AccountService {

    Account findByNumber(String number);

    void save(Account account);

    List<Account> findAll();

    List<AccountDto> map(List<Account> accounts);

    Account findById(Long id);
}
