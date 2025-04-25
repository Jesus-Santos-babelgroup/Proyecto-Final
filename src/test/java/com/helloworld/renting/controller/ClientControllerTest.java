package com.helloworld.renting.controller;

import com.helloworld.renting.exceptions.RentingException;
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

        Mockito.doThrow(new RentingException("Cliente no encontrado"))
                .when(clientService).deleteClientById(id);

        mockMvc.perform(delete("/api/clients/{id}", id))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("✅ Borrar cliente por CIF exitosamente")
    void deleteClientByCifSuccess() throws Exception {
        String cif = "B12345678";

        mockMvc.perform(delete("/api/clients").param("cif", cif))
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    @DisplayName("❌ Borrar cliente por CIF - formato inválido")
    void deleteClientByCifBadFormat() throws Exception {
        String cif = "BAD123";

        Mockito.doThrow(new RentingException("Formato de CIF inválido"))
                .when(clientService).deleteClientByCIF(cif);

        mockMvc.perform(delete("/api/clients").param("cif", cif))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("❌ Borrar cliente por CIF - no existe")
    void deleteClientByCifNotFound() throws Exception {
        String cif = "Z9999999X";

        Mockito.doThrow(new RentingException("Cliente no encontrado por CIF"))
                .when(clientService).deleteClientByCIF(cif);

        mockMvc.perform(delete("/api/clients").param("cif", cif))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("❌ No se puede eliminar cliente con solicitudes pendientes")
    void deleteClientWithPendingRequests() throws Exception {
        Long clientId = 99L;

        Mockito.doThrow(new RentingException("Cliente con solicitudes pendientes. No se puede eliminar."))
                .when(clientService).deleteClientById(clientId);

        mockMvc.perform(delete("/api/clients/{id}", clientId))
                .andExpect(status().isNotFound());
    }
}
