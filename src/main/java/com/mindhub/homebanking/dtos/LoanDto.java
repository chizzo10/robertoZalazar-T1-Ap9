package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDto {

    @Id
    @GenericGenerator(name ="native",strategy ="native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private String name;
    private Double maxAmount;
    @Column(name ="payments")
    @ElementCollection
    private List<Integer>payments=new ArrayList<>();
    private Set<ClientLoanDto>clientLoan;


    public LoanDto(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.clientLoan=loan.getClientLoans()
                .stream()
                .map(loanClient -> new ClientLoanDto(loanClient))
                .collect(Collectors.toSet());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public void setClientLoan(Set<ClientLoanDto> clientLoan) {
        this.clientLoan = clientLoan;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Set<ClientLoanDto> getClientLoan() {
        return clientLoan;
    }
}
