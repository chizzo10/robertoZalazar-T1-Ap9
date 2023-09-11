package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientsDto;
import com.mindhub.homebanking.models.Client;


import java.util.List;

public interface ClientService {


    List<ClientsDto> getClientsDto();

    Client findById(Long id);

    ClientsDto getClientsDto(Long id);

    void save(Client client );

    Client findByEmail(String email);

    ClientsDto getAll(String email);

}
