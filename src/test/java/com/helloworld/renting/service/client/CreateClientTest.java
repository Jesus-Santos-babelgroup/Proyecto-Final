package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private StructMapperToDto toDto;

    @Mock
    private StructMapperToEntity toEntity;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private AddressMapper addressMapper; // Agregar el mock

    @InjectMocks
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    @Test
    void createClient_BasicFunctionality() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");
        inputDto.setScoring(100); // Valor válido para scoring
        inputDto.setCountryId("1"); // ID de país válido como String
        inputDto.setAddressId(1L); // Agregar ID de dirección válido

        Client client = new Client();
        ClientDto outputDto = new ClientDto();
        outputDto.setId(1L);

        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(toEntity.toEntity(inputDto)).thenReturn(client);
        when(toDto.toDto(client)).thenReturn(outputDto);

        // Mockear validaciones
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true); // Añadir mock de dirección

        // When
        ClientDto result = clientService.createClient(inputDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verificamos que los métodos se llamaron con los parámetros correctos
        verify(clientMapper).existsByNif(inputDto.getNif());
        verify(clientMapper).existsByEmail(inputDto.getEmail());
        verify(toEntity).toEntity(inputDto);
        verify(clientMapper).insert(client);
        verify(toDto).toDto(client);
        verify(countryMapper).existsByCountryId(Long.valueOf(inputDto.getCountryId()));
        verify(addressMapper).existsByAddressId(inputDto.getAddressId()); // Verificar que se llama al método
    }

    @Test
    void createClient_ShouldInsertClientCorrectly() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");
        inputDto.setName("Juan");
        inputDto.setScoring(50); // Valor válido para scoring
        inputDto.setCountryId("1"); // Agregar ID de país válido
        inputDto.setAddressId(1L); // Agregar ID de dirección válido

        Client mappedClient = new Client();
        mappedClient.setNif("12345678A");

        when(clientMapper.existsByNif(any())).thenReturn(false);
        when(clientMapper.existsByEmail(any())).thenReturn(false);
        when(toEntity.toEntity(inputDto)).thenReturn(mappedClient);
        when(toDto.toDto(any())).thenReturn(new ClientDto());

        // Mockear validaciones
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true);

        // When
        clientService.createClient(inputDto);

        // Then
        verify(clientMapper).insert(clientCaptor.capture());
        Client insertedClient = clientCaptor.getValue();
        assertEquals("12345678A", insertedClient.getNif());
    }

    @Test
    void createClient_WithNullDto_ShouldThrowException() {
        // When & Then
        assertThrows(InvalidClientDtoException.class,
                () -> clientService.createClient(null));

        // Verificamos que no se llamaron otros métodos
        verifyNoInteractions(toEntity, toDto);
        verify(clientMapper, never()).insert(any());
    }

    @Test
    void createClient_WithDuplicateNif_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");

        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains(inputDto.getNif()));
        verify(clientMapper, never()).insert(any());
    }

    @Test
    void createClient_WithDuplicateEmail_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");

        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains(inputDto.getEmail()));
        verify(clientMapper, never()).insert(any());
    }


    @Test
    void createClient_WithFutureBirthDate_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");
        inputDto.setName("Juan");

        // Establecer una fecha de nacimiento futura
        LocalDate futureDate = LocalDate.now().plusYears(1);
        inputDto.setDateOfBirth(futureDate);
        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains("fecha de nacimiento"));
        verify(clientMapper, never()).insert(any());
    }
    @Test
    void createClient_WithInvalidAddress_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");
        inputDto.setScoring(100);
        inputDto.setCountryId("1");
        inputDto.setAddressId(999L); // Dirección inexistente

        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(false); // Mock de dirección inexistente

        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains("La dirección con ID"));
        verify(clientMapper, never()).insert(any());
    }
    @Test
    void createClient_WithDuplicatePhone_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");
        inputDto.setPhone("666123456");

        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(clientMapper.existsByPhone(inputDto.getPhone())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains(inputDto.getPhone()));
        verify(clientMapper, never()).insert(any());
    }
}