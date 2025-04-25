package com.helloworld.renting.service.client;

import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.ClientMapper;
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
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private ClientDto validClientDto;

    @BeforeEach
    void setUp() {
        // Configurar un ClientDto válido para usar en las pruebas
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
    }

    @Test
    void createClient_WithValidData_ShouldReturnClientDto() {
        // Given
        when(clientMapper.existsByNif(validClientDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(validClientDto.getEmail())).thenReturn(false);
        doAnswer(invocation -> {
            Client client = invocation.getArgument(0);
            client.setId(1L); // Simular la asignación de ID que haría la base de datos
            return 1; // Retornar 1 fila afectada
        }).when(clientMapper).insert(any(Client.class));

        // When
        ClientDto result = clientService.createClient(validClientDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getName());
        assertEquals("García", result.getFirstSurname());
        assertEquals("12345678A", result.getNif());

        verify(clientMapper).existsByNif(validClientDto.getNif());
        verify(clientMapper).existsByEmail(validClientDto.getEmail());
        verify(clientMapper).insert(any(Client.class));
    }

    @Test
    void createClient_WithNullDto_ShouldThrowInvalidClientDtoException() {
        // When & Then
        InvalidClientDtoException exception = assertThrows(
                InvalidClientDtoException.class,
                () -> clientService.createClient(null)
        );

        assertEquals("El DTO del cliente no puede ser nulo", exception.getMessage());
        verify(clientMapper, never()).insert(any(Client.class));
    }

    @Test
    void createClient_WithDuplicateNif_ShouldThrowDuplicateModelException() {
        // Given
        when(clientMapper.existsByNif(validClientDto.getNif())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(
                DuplicateModel.class,
                () -> clientService.createClient(validClientDto)
        );

        assertEquals("Ya existe un cliente con este NIF: 12345678A", exception.getMessage());
        verify(clientMapper, never()).insert(any(Client.class));
    }

    @Test
    void createClient_WithDuplicateEmail_ShouldThrowDuplicateModelException() {
        // Given
        when(clientMapper.existsByNif(validClientDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(validClientDto.getEmail())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(
                DuplicateModel.class,
                () -> clientService.createClient(validClientDto)
        );

        assertEquals("Ya existe un cliente con este email: juan@example.com", exception.getMessage());
        verify(clientMapper, never()).insert(any(Client.class));
    }
}