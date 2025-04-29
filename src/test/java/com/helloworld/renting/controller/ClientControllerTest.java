package com.helloworld.renting.controller;

import com.helloworld.renting.exceptions.attributes.ClientWithPendingRequestsException;
import com.helloworld.renting.exceptions.notfound.ClientNotFoundException;
import com.helloworld.renting.service.client.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("✅ Borrar cliente por ID exitosamente")
    void deleteClientByIdSuccess() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/clients/{id}", id))
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    @DisplayName("❌ Borrar cliente por ID - no existe")
    void deleteClientByIdNotFound() throws Exception {
        Long id = 999L;

        Mockito.doThrow(new ClientNotFoundException("Cliente no encontrado"))
                .when(clientService).deleteClientById(id);

        mockMvc.perform(delete("/api/clients/{id}", id))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("✅ Borrar cliente por NIF exitosamente")
    void deleteClientByNifSuccess() throws Exception {
        String nif = "B12345678";

        mockMvc.perform(delete("/api/clients").param("nif", nif))
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    @DisplayName("❌ Borrar cliente por NIF - no existe")
    void deleteClientByNifNotFound() throws Exception {
        String nif = "Z9999999X";

        Mockito.doThrow(new ClientNotFoundException("Cliente no encontrado por NIF"))
                .when(clientService).deleteClientByNif(nif);

        mockMvc.perform(delete("/api/clients").param("nif", nif))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("❌ No se puede eliminar cliente con solicitudes registradas")
    void deleteClientWithRequests() throws Exception {
        Long clientId = 99L;

        Mockito.doThrow(new ClientWithPendingRequestsException("Cliente con solicitudes registradas"))
                .when(clientService).deleteClientById(clientId);

        mockMvc.perform(delete("/api/clients/{id}", clientId))
                .andExpect(status().isConflict()); // 409
    }
}
