package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientsDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<ClientsDto> getClients(){
       List<Client> allClients =  clientRepository.findAll();

       List<ClientsDto> clientsDtoconvertList = allClients
               .stream()
               .map(Client -> new ClientsDto(Client))
               .collect(Collectors.toList());
        return clientsDtoconvertList;
    }

    @GetMapping("/clients/{id}")
    public ClientsDto getClientById(@PathVariable Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return new ClientsDto(clientOptional.get());
    }

}
