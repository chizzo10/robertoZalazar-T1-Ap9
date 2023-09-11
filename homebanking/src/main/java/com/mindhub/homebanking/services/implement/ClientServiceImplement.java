package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientsDto;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public List<ClientsDto> getClientsDto() {
        return clientRepository.findAll().stream()
                .map(Client -> new ClientsDto(Client))
                .collect(Collectors.toList());
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClientsDto getClientsDto(Long id) {
        return new ClientsDto(this.findById(id));
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientsDto getAll(String email) {
        return new ClientsDto(clientRepository.findByEmail(email));
    }


}
