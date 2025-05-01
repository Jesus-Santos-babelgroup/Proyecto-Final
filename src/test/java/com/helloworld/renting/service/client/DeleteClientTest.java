package com.helloworld.renting.service.client;

import com.helloworld.renting.controller.ClientController;
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
class DeleteClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("✅ Borrar cliente por ID exitosamente")
    void deleteClientByIdSuccess() throws Exception {
        mockMvc.perform(delete("/api/clients/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("❌ Borrar cliente por ID - no existe")
    void deleteClientByIdNotFound() throws Exception {
        Mockito.doThrow(new ClientNotFoundException("Cliente no encontrado"))
                .when(clientService).deleteClientById(999L);

        mockMvc.perform(delete("/api/clients/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    /*
    @Test
    @DisplayName("❌ Borrar cliente por ID - tiene solicitudes registradas")
    void deleteClientByIdWithPendingRequests() throws Exception {
        Mockito.doThrow(new ClientWithPendingRequestsException("Solicitudes pendientes"))
                .when(clientService).deleteClientById(100L);

        mockMvc.perform(delete("/api/clients/{id}", 100L))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("✅ Borrar cliente por NIF exitosamente")
    void deleteClientByNifSuccess() throws Exception {
        mockMvc.perform(delete("/api/clients?nif=12345678A"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("❌ Borrar cliente por NIF - formato inválido")
    void deleteClientByNifInvalidFormat() throws Exception {
        mockMvc.perform(delete("/api/clients?nif=BAD123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("❌ Borrar cliente por NIF - no encontrado")
    void deleteClientByNifNotFound() throws Exception {
        Mockito.doThrow(new ClientNotFoundException("Cliente no encontrado"))
                .when(clientService).deleteClientByNif("Z9999999X");

        mockMvc.perform(delete("/api/clients?nif=Z9999999X"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("❌ Borrar cliente por NIF - tiene solicitudes registradas")
    void deleteClientByNifWithPendingRequests() throws Exception {
        Mockito.doThrow(new ClientWithPendingRequestsException("Solicitudes pendientes"))
                .when(clientService).deleteClientByNif("12345678X");

        mockMvc.perform(delete("/api/clients?nif=12345678X"))
                .andExpect(status().isConflict());
    }
    */
}
