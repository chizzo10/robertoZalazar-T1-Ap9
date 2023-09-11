package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDto;
import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<CardDto> getCards();

    Card findCardByNumber(String number);

    void save(Card card);
}
