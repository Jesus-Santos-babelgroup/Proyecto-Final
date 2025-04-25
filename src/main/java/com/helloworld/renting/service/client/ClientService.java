package com.helloworld.renting.service.client;

import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.RentingException;
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
            throw new RentingException("Cliente no encontrado");
        }

        if (requestRepository.countPendingByClientId(id) > 0) {
            throw new RentingException("Cliente con solicitudes pendientes. No se puede eliminar.");
        }

        clientRepository.delete(id);
    }

    public void deleteClientByCIF(String cif) {
        if (!cif.matches("^[A-Z][0-9]{7}[A-Z0-9]$")) {
            throw new RentingException("Formato de CIF inválido");
        }

        Client client = clientRepository.findByCif(cif);
        if (client == null) {
            throw new RentingException("Cliente no encontrado");
        }

        if (requestRepository.countPendingByClientId(client.getId()) > 0) {
            throw new RentingException("Cliente con solicitudes pendientes. No se puede eliminar.");
        }

        clientRepository.delete(client.getId());
    }
}
