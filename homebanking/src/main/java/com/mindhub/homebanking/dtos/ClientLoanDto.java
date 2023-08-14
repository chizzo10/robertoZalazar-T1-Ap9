package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;

public class ClientLoanDto {
    private Long id;
    private Long id_Loan;
    private String name;
    private Double amount;

    private Integer payments ;
    private Set<ClientsDto> clientLoans = new HashSet<>();
    public ClientLoanDto(ClientLoan clientLoan){
        id=clientLoan.getId();
        id_Loan= clientLoan.getLoan().getId();
        name= clientLoan.getLoan().getName();
        amount= clientLoan.getAmount();
        payments=clientLoan.getPayments();
    }
    public Long getId() {
        return id;
    }

    public Long getId_Loan() {
        return id_Loan;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Set<ClientsDto> getClientLoans() {
        return clientLoans;
    }
}
