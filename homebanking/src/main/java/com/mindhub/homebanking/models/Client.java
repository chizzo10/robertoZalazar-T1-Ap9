package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;


    @OneToMany(mappedBy = "user", fetch= FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();


@OneToMany(mappedBy ="client", fetch = FetchType.EAGER)
private Set<ClientLoan> clientLoans = new HashSet<>();

    public Client() {
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }



    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }



    public void addAccount(Account account){
        account.setUser(this);
accounts.add(account);
    }

    public List<Loan> getLoans(){
        return clientLoans.stream().map(loan -> loan.getLoan()).collect(toList());
    }
    public void addLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);

    }
}
