package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Transactional
    public ClientDto createClient(ClientDto clientDto) {

        if (clientDto == null) {
            throw new InvalidClientDtoException("El DTO del cliente no puede ser nulo");
        }

        if (clientRepository.existsByNif(clientDto.getNif())) {
            throw new DuplicateModel("Ya existe un cliente con este NIF: " + clientDto.getNif());
        }

        if (clientRepository.existsByEmail(clientDto.getEmail())) {
            throw new DuplicateModel("Ya existe un cliente con este email: " + clientDto.getEmail());
        }

        Client client = new Client();
        client.setName(clientDto.getName());
        client.setFirstSurname(clientDto.getFirstSurname());
        client.setSecondSurname(clientDto.getSecondSurname());
        client.setAddressId(clientDto.getAddressId());
        client.setCountryId(clientDto.getCountryId());
        client.setPhone(clientDto.getPhone());
        client.setNif(clientDto.getNif());
        client.setDateOfBirth(clientDto.getDateOfBirth());
        client.setEmail(clientDto.getEmail());
        client.setScoring(clientDto.getScoring());


        Client savedClient = clientRepository.save(client);

        // Convertir entidad a DTO
        ClientDto savedClientDto = new ClientDto();
        savedClientDto.setId(savedClient.getId());
        savedClientDto.setName(savedClient.getName());
        savedClientDto.setFirstSurname(savedClient.getFirstSurname());
        savedClientDto.setSecondSurname(savedClient.getSecondSurname());
        savedClientDto.setAddressId(savedClient.getAddressId());
        savedClientDto.setCountryId(savedClient.getCountryId());
        savedClientDto.setPhone(savedClient.getPhone());
        savedClientDto.setNif(savedClient.getNif());
        savedClientDto.setDateOfBirth(savedClient.getDateOfBirth());
        savedClientDto.setEmail(savedClient.getEmail());
        savedClientDto.setScoring(savedClient.getScoring());

        return savedClientDto;

    }
}