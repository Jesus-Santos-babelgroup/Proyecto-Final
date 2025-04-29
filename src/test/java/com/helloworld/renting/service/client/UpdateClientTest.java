package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DBException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClientTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private StructMapperToDto toDto;

    @Mock
    private StructMapperToEntity toEntity;

    @InjectMocks
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    @Test
    void updateClient_BasicFunctionality() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setPhone("+34123456789");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setScoring(750);
        inputDto.setCountryId("1");
        inputDto.setAddressId(1L);
        inputDto.setNotificationAddressId(1L);

        Client client = new Client();
        ClientDto outputDto = new ClientDto();
        outputDto.setId(1L);

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(clientMapper.existsByPhone(inputDto.getPhone())).thenReturn(false);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true);
        when(toEntity.toEntity(inputDto)).thenReturn(client);
        when(toDto.toDto(client)).thenReturn(outputDto);

        // When
        ClientDto result = clientService.updateClient(inputDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verificamos que los métodos se llamaron con los parámetros correctos
        verify(clientMapper).existsByEmail(inputDto.getEmail());
        verify(clientMapper).existsById(inputDto.getId());
        verify(clientMapper).existsByNif(inputDto.getNif());
        verify(clientMapper).existsByPhone(inputDto.getPhone());
        verify(toEntity).toEntity(inputDto);
        verify(clientMapper).updateClient(client);
        verify(toDto).toDto(client);
        verify(countryMapper).existsByCountryId(Long.valueOf(inputDto.getCountryId()));
        verify(addressMapper).existsByAddressId(inputDto.getAddressId());
    }

    @Test
    void updateClient_IDNotExists_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        when(clientMapper.existsById(inputDto.getId())).thenReturn(false);


        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El ID no existe"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullName_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El nombre no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullFirstSurName_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);

        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Los apellidos no pueden ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullPhone_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El tlf no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullEmail_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El email no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullNif_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El NIF no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullDateBirth_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("La fecha de nacimiento no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NotInBBDDIDCountry_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setCountryId("0");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(false);


        // When & Then
        DBException exception = assertThrows(DBException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El país con ID " + inputDto.getCountryId() + " no existe en la base de datos"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullIDAddress_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setCountryId("68");

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);


        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El ID de la dirección no puede ser nulo"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NotInBBDDIDAddress_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setCountryId("68");
        inputDto.setAddressId(1L);
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(false);


        // When & Then
        DBException exception = assertThrows(DBException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("La dirección con ID " + inputDto.getAddressId() + " no existe en la base de datos"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_DuplicateNif_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setAddressId(1L);
        inputDto.setCountryId("68");

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este NIF: " + inputDto.getNif()));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_DuplicateEmail_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setAddressId(1L);
        inputDto.setCountryId("68");

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este email: " + inputDto.getEmail()));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_DuplicatePhone_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("12345678A");
        inputDto.setPhone("+34123456789");
        inputDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        inputDto.setAddressId(1L);
        inputDto.setCountryId("68");

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(countryMapper.existsByCountryId(Long.valueOf(inputDto.getCountryId()))).thenReturn(true);
        when(addressMapper.existsByAddressId(inputDto.getAddressId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(false);
        when(clientMapper.existsByPhone(inputDto.getPhone())).thenReturn(true);


        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este teléfono: " + inputDto.getPhone()));
        verify(clientMapper, never()).updateClient(any());
    }

}

