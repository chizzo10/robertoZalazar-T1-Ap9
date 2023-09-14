package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientsDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDto> accounts;
    private Set<ClientLoanDto> loans;
    private Set<CardDto> cards;
    public ClientsDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client
                .getAccounts()
                .stream()
                .map(account ->new AccountDto(account))
                .collect(Collectors.toSet());
        this.loans = client.getClientLoans()
                .stream().map(loan -> new ClientLoanDto(loan))
                .collect(Collectors.toSet());
        this.cards= client.getCards()
                .stream().map(card -> new CardDto(card))
                .collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Set<ClientLoanDto> getLoans() {
        return loans;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<CardDto> getCards() {
        return cards;
    }

    public Set<AccountDto> getAccounts() {
        return accounts;
    }

    public String getEmail() {
        return email;
    }
}
