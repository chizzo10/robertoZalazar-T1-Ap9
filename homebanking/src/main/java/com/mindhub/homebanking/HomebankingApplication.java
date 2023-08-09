package com.mindhub.homebanking;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {



	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository  transactionRepository){
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

		};
	}

}
