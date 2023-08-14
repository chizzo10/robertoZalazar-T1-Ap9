package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {



	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository  transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
		return (args) ->{
			Client client1 = new Client("Melba", "Morel", "melba@minhub.com");
			Client client2=new Client("Roberto", "Zalazar","servicios773@gmail.com" );
			clientRepository.save(client1);
			clientRepository.save(client2);



			Account account = new Account("VIN001", LocalDate.now(),5000);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);


			client1.addAccount(account);
			client1.addAccount(account2);
			accountRepository.save(account);
			accountRepository.save(account2);


			Transaction transaction = new Transaction(TransactionType.DEBIT, -500,  "Farmacia",LocalDate.now());
			account.addTransaction(transaction);
			transactionRepository.save(transaction);


			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 1000,  "SuperMercado",LocalDate.now());
			account2.addTransaction(transaction2);
			transactionRepository.save(transaction2);






			Loan Loan1 = new Loan("Personal",100000, List.of(6,12,24));
			loanRepository.save(Loan1);

			Loan Loan2 =new Loan("Automotriz",300000,List.of(6,12,24,36));
			loanRepository.save(Loan2);

			Loan Loan3 =new Loan("Hipotecario",500000,List.of(12,24,36,48,60));
			loanRepository.save(Loan3);




			ClientLoan ClientLoanMelba1 = new  ClientLoan(400000.00,60,client1,Loan1);
			clientLoanRepository.save(ClientLoanMelba1);

			ClientLoan ClientLoanMelba2 = new ClientLoan(100000.00, 24,client1,Loan2);
			clientLoanRepository.save(ClientLoanMelba2);

			ClientLoan ClientLoanClient2 = new ClientLoan(200000.00,36,client2,Loan3);
			clientLoanRepository.save(ClientLoanClient2);


		};
	}

}
