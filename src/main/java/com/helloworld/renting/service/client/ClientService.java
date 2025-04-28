package com.helloworld.renting.service.client;

import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;
import com.helloworld.renting.exceptions.attributes.ClientWithPendingRequestsException;
import com.helloworld.renting.repository.ClientRepository;
import com.helloworld.renting.repository.RequestRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RequestRepository requestRepository;

    public ClientService(ClientRepository clientRepository, RequestRepository requestRepository) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
    }

    public void deleteClientById(Long id) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new ClientNotFoundException("Cliente no encontrado");
        }
        if (requestRepository.countPendingByClientId(id) > 0) {
            throw new ClientWithPendingRequestsException("El cliente tiene solicitudes pendientes");
        }
        clientRepository.delete(id);
    }

    public void deleteClientByNif(String nif) {
        if (!nif.matches("^[0-9]{8}[A-Z]$")) {
            throw new IllegalArgumentException("Formato de NIF inválido");
        }
        Client client = clientRepository.findByNif(nif);
        if (client == null) {
            throw new ClientNotFoundException("Cliente no encontrado");
        }
        if (requestRepository.countPendingByClientId(client.getId()) > 0) {
            throw new ClientWithPendingRequestsException("El cliente tiene solicitudes pendientes");
        }
        clientRepository.delete(client.getId());
    }
}
