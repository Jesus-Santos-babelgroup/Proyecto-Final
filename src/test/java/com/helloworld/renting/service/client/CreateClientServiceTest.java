package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private ClientDto validClientDto;
    private Client savedClient;

    @BeforeEach
    void setUp() {
        // Configure a valid ClientDto to use in testing
        validClientDto = new ClientDto();
        validClientDto.setName("Juan");
        validClientDto.setFirstSurname("García");
        validClientDto.setSecondSurname("López");
        validClientDto.setAddressId(1L);
        validClientDto.setCountryId(1L);
        validClientDto.setPhone("666555444");
        validClientDto.setNif("12345678A");
        validClientDto.setDateOfBirth(LocalDate.of(1990, 1, 15));
        validClientDto.setEmail("juan@example.com");
        validClientDto.setScoring(80);

        // Configure the Client to be returned after saving it
        savedClient = new Client();
        savedClient.setId(1L);
        savedClient.setName("Juan");
        savedClient.setFirstSurname("García");
        savedClient.setSecondSurname("López");
        savedClient.setAddressId(1L);
        savedClient.setCountryId(1L);
        savedClient.setPhone("666555444");
        savedClient.setNif("12345678A");
        savedClient.setDateOfBirth(LocalDate.of(1990, 1, 15));
        savedClient.setEmail("juan@example.com");
        savedClient.setScoring(80);
    }

    @Test
    void createClient_WithValidData_ShouldReturnClientDto() {
        // Given
        when(clientRepository.existsByNif(validClientDto.getNif())).thenReturn(false);
        when(clientRepository.existsByEmail(validClientDto.getEmail())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // When
        ClientDto result = clientService.createClient(validClientDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getName());
        assertEquals("García", result.getFirstSurname());
        assertEquals("12345678A", result.getNif());
    }

    @Test
    void createClient_WithNullDto_ShouldThrowInvalidClientDtoException() {
        // When & Then
        InvalidClientDtoException exception = assertThrows(
                InvalidClientDtoException.class,
                () -> clientService.createClient(null)
        );

        assertEquals("El DTO del cliente no puede ser nulo", exception.getMessage());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void createClient_WithDuplicateNif_ShouldThrowDuplicateModelException() {
        // Given
        when(clientRepository.existsByNif(validClientDto.getNif())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(
                DuplicateModel.class,
                () -> clientService.createClient(validClientDto)
        );

        assertEquals("Ya existe un cliente con este NIF: 12345678A", exception.getMessage());
    }

    @Test
    void createClient_WithDuplicateEmail_ShouldThrowDuplicateModelException() {
        // Given
        when(clientRepository.existsByNif(validClientDto.getNif())).thenReturn(false);
        when(clientRepository.existsByEmail(validClientDto.getEmail())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(
                DuplicateModel.class,
                () -> clientService.createClient(validClientDto)
        );

        assertEquals("Ya existe un cliente con este email: juan@example.com", exception.getMessage());
    }
}