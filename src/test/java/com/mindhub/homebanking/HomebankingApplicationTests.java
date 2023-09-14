package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootApplication
class HomebankingApplicationTests {

    @DataJpaTest

    @AutoConfigureTestDatabase(replace = NONE)

    public class RepositoriesTest {


        @Autowired

        LoanRepository loanRepository;

        @Autowired
        AccountRepository accountRepository;

        @Autowired
        CardRepository cardRepository;

        @Autowired
        TransactionRepository transactionRepository;


        @Test

        public void existLoans() {

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, is(not(empty())));

        }


        @Test

        public void existPersonalLoan() {

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

        }

        @Test
        public void existCard() {
            List<Card> card = cardRepository.findAll();
            assertThat(card, is(not(empty())));
        }

        @Test
        public void cardNumberNotNull() {
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, is(not(nullValue())));
        }


        @Test
        public void existTransaction() {
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions, is(not(empty())));
        }


        @Test
        public void existTransactionAccount() {
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions, allOf(hasItem(hasProperty("account", is(notNullValue())))));
        }


        @Test
        public void existAccount() {
            List<Account> account = accountRepository.findAll();
            assertThat(account, is(not(empty())));
        }


        @Test
        public void numberAccountNotNull() {
            List<Account> account = accountRepository.findAll();
            assertThat(account, allOf(hasItem(hasProperty("number", is(notNullValue())))));
        }


    }


}