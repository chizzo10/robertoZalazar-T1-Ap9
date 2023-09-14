package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String CardHolder;
    private CardType type;

    private CardColor color;

    private String number;
    private Integer cvv;
    private LocalDateTime fromDate = LocalDateTime.now();
    private LocalDateTime thruDate = LocalDateTime.now();

@ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, Integer cvv, LocalDateTime fromDate, LocalDateTime thruDate) {
        CardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return CardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
