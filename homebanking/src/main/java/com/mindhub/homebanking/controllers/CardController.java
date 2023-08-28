package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @GetMapping("/cards")
    public List<CardDto> getCards() {
        return cardRepository.findAll().stream()
                .map(currentCard -> new CardDto(currentCard))
                .collect(Collectors.toList());

    }
    @PostMapping ("/clients/current/cards")
    public ResponseEntity<Object> createCard (Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
        }


        Set<Card> cards = client.getCards();

        int cardLimit;
        if (cardType.equals(CardType.CREDIT) || cardType.equals(CardType.DEBIT)) {
            cardLimit = 3;

            long cardsSameType = cards.stream()
                    .filter(newCard -> newCard.getType() == cardType)
                    .count();
            if (cardsSameType >= cardLimit) {
                return new ResponseEntity<>("Max amount of card reached", HttpStatus.FORBIDDEN);
            }
            boolean colorUsed = cards.stream()
                    .anyMatch(card -> card.getType() == cardType && card.getColor() == cardColor);
            if (colorUsed) {
                return new ResponseEntity<>("Color already used", HttpStatus.FORBIDDEN);
            }
        }


        String randomCardNumber;
        do {
            Random random = new Random();
            randomCardNumber = random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999);
        } while (cardRepository.findCardByNumber(randomCardNumber) != null);
        int randomCvvNumber = new Random().nextInt(1000);


        Card card = new Card(client.getFirstName(), cardType, cardColor, randomCardNumber, randomCvvNumber, LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        client.addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>("Account created succesfully", HttpStatus.CREATED);
    }


}



