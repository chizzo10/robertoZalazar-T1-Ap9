package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDto> getLoans() {
        return loanService.getLoans();
    }

    @PostMapping("/loans")
    @Transactional
    public ResponseEntity<Object> LoanApplication(
            @RequestBody LoanApplicationDto loanApplicationDto,
            Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        double requestedAmount = loanApplicationDto.getAmount();
        int requestedPayments = loanApplicationDto.getPayments();
        long loanId = loanApplicationDto.getLoanId();
        String toAccountNumber = loanApplicationDto.getToAccountNumber();

        Loan loan = loanService.findLoanById(loanId);

        if (requestedAmount <= 0 || requestedPayments <= 0 || loan == null) {
            return new ResponseEntity<>("Los Datos son incorrectos o el préstamo no existe", HttpStatus.BAD_REQUEST);
        }

        if (loan.getMaxAmount() < requestedAmount) {
            return new ResponseEntity<>("Monto excedido al permitido", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(requestedPayments)) {
            return new ResponseEntity<>("Cantidad de cuotas no permitidas", HttpStatus.FORBIDDEN);
        }

        Account toAccount = accountService.findByNumber(toAccountNumber);

        if (toAccount == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.BAD_REQUEST);
        }

        if (!toAccount.getUser().equals(client)) {
            return new ResponseEntity<>("La cuenta de destino inexistente", HttpStatus.BAD_REQUEST);
        }

        double credit = requestedAmount + (0.20 * requestedAmount);
        String description = loan.getName() + " Loan approved";

        ClientLoan newLoan = new ClientLoan(credit, requestedPayments, client, loan);
        loan.addLoans(newLoan);
        client.addLoan(newLoan);
        clientService.save(client);

        double currentBalance = toAccount.getBalance();
        toAccount.setBalance(currentBalance + credit);

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT, credit, description, LocalDate.now());
        toAccount.addTransaction(transactionCredit);
        transactionService.save(transactionCredit);
        accountService.save(toAccount);
        clientLoanService.save(newLoan);

        return new ResponseEntity<>("Prestamo Aprobado", HttpStatus.ACCEPTED);
    }
}

