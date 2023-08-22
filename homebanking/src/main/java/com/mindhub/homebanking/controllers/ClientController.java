package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientsDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;;

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


    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/clients/current")

    public ClientsDto getAll(Authentication authentication) {

        return new ClientsDto(clientRepository.findByEmail(authentication.getName())) ;
    }

}
