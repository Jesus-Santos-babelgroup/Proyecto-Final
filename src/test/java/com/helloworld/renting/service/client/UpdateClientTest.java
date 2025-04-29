package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
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
        inputDto.setCountryId("ES");
        inputDto.setAddressId(100L);
        inputDto.setNotificationAddressId(101L);

        Client client = new Client();
        ClientDto outputDto = new ClientDto();
        outputDto.setId(1L);

        when(clientMapper.existById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existByNif(inputDto.getNif())).thenReturn(false);
        when(clientMapper.existByEmail(inputDto.getEmail())).thenReturn(false);
        when(clientMapper.existByTlf(inputDto.getPhone())).thenReturn(false);
        when(toEntity.toEntity(inputDto)).thenReturn(client);
        when(toDto.toDto(client)).thenReturn(outputDto);

        // When
        ClientDto result = clientService.updateClient(inputDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        // Verificamos que los métodos se llamaron con los parámetros correctos
        verify(clientMapper).existByEmail(inputDto.getEmail());
        verify(clientMapper).existById(inputDto.getId());
        verify(clientMapper).existByNif(inputDto.getNif());
        verify(clientMapper).existByTlf(inputDto.getPhone());
        verify(toEntity).toEntity(inputDto);
        verify(clientMapper).updateClient(client);
        verify(toDto).toDto(client);
    }

    @Test
    void updateClient_IDNotExists_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();


        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("El ID no existe"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullName_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("El nombre no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullFirstSurName_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);

        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

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
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

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
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

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
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

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
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("La fecha de nacimiento no puede ser NULL"));
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
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("La dirección no puede ser NULL"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void updateClient_NullIDCountry_ShouldThrowException() {

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
        inputDto.setAddressId(100L);
        when(clientMapper.existById(inputDto.getId())).thenReturn(true);


        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("El país no puede ser NULL"));
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
        inputDto.setAddressId(100L);
        inputDto.setCountryId("ES");

        when(clientMapper.existById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existByNif(inputDto.getNif())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("Este NIF ya existe"));
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
        inputDto.setAddressId(100L);
        inputDto.setCountryId("ES");

        when(clientMapper.existById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existByEmail(inputDto.getEmail())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("Este email ya existe"));
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
        inputDto.setAddressId(100L);
        inputDto.setCountryId("ES");

        when(clientMapper.existById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existByTlf(inputDto.getPhone())).thenReturn(true);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        assertTrue(exception.getMessage().contains("Este tlf ya existe"));
        verify(clientMapper, never()).updateClient(any());
    }


}

