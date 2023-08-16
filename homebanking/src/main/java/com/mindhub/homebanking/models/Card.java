package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate thruDate;
    private LocalDate fromDate;

@ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, Integer cvv, LocalDate thruDate, LocalDate fromDate) {
        CardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
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

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
