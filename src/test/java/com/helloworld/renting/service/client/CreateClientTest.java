package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.AddressDto;
import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.dto.CountryDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.exceptions.attributes.AttributeException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.mapper.*;
import com.helloworld.renting.service.address.AddressService;
import com.helloworld.renting.service.country.CountryService;
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
    private CountryMapper countryMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private StructMapperToDto toDto;

    @Mock
    private StructMapperToEntity toEntity;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private AddressService addressService;

    @Mock
    private CountryService countryService;

    @Captor
    private ArgumentCaptor<Client> clientCaptor;


    @Test
    void createClient_BasicFunctionality() {
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
        inputDto.setScoring(7);

        AddressDto address = new AddressDto();
        address.setId(1L);
        address.setCity("Madrid");
        address.setStreet("Calle Mayor, 45");
        address.setZipCode("28013");

        CountryDto country = new CountryDto();
        country.setIsoA2("AL");

        inputDto.setAddress(address);
        inputDto.setCountry(country);
        inputDto.setNotificationAddress(address);

        Client client = new Client();
        ClientDto outputDto = new ClientDto();
        outputDto.setId(1L);
        outputDto.setNotificationAddress(address);

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(1);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(1);
        when(clientMapper.existsByPhone(inputDto.getPhone())).thenReturn(1);
        when(countryMapper.existsByISOA2(country.getIsoA2())).thenReturn(true);
        when(addressMapper.existsByCity(address.getCity())).thenReturn(true);
        when(toEntity.clientToEntity(any(ClientDto.class))).thenReturn(client);
        when(toDto.clientToDto(client)).thenReturn(outputDto);
        // When
        ClientDto result = clientService.createClient(inputDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(clientMapper).existsByEmail(inputDto.getEmail());
        verify(clientMapper).existsById(inputDto.getId());
        verify(clientMapper).existsByNif(inputDto.getNif());
        verify(clientMapper).existsByPhone(inputDto.getPhone());
        verify(toEntity).clientToEntity(inputDto);
        verify(clientMapper).insert(client);
        verify(toDto).clientToDto(client);

    }

    @Test
    void createClient_ShouldInsertClientCorrectly() {
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
        inputDto.setScoring(7);

        AddressDto address = new AddressDto();
        address.setId(1L);
        address.setCity("Madrid");
        address.setStreet("Calle Mayor, 45");
        address.setZipCode("28013");

        CountryDto country = new CountryDto();
        country.setIsoA2("AL");

        inputDto.setAddress(address);
        inputDto.setCountry(country);
        inputDto.setNotificationAddress(address);

        Client mappedClient = new Client();
        mappedClient.setNif("12345678A");


        when(clientMapper.existsByNif(any())).thenReturn(1);
        when(clientMapper.existsByEmail(any())).thenReturn(1);
        when(toEntity.clientToEntity(inputDto)).thenReturn(mappedClient);
        when(toDto.clientToDto(any())).thenReturn(new ClientDto());
      
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
    void createClient_IDNotExists_ShouldThrowException() {

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
    void createClient_NullName_ShouldThrowException() {

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
    void createClient_NullFirstSurName_ShouldThrowException() {

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
    void createClient_NullPhone_ShouldThrowException() {

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
    void createClient_NullEmail_ShouldThrowException() {

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
    void createClient_NullNif_ShouldThrowException() {

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
    void createClient_NullDateBirth_ShouldThrowException() {

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
    void createClient_BadFormatNif_ShouldThrowException() {

        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");
        inputDto.setNif("1");
        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);

        // When & Then
        AttributeException exception = assertThrows(AttributeException.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("El NIF no tiene el formato correcto"));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void createClient_WithDuplicateNif_ShouldThrowException() {
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

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(2);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este NIF: " + inputDto.getNif()));
        verify(clientMapper, never()).updateClient(any());
    }

    @Test
    void createClient_WithDuplicateEmail_ShouldThrowException() {
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


        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(1);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(2);

        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este email: " + inputDto.getEmail()));
        verify(clientMapper, never()).updateClient(any());
    }


    @Test
    void createClient_WithFutureBirthDate_ShouldThrowException() {
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
        LocalDate futureDate = LocalDate.now().plusYears(1);
        inputDto.setDateOfBirth(futureDate);

        // When & Then
        InvalidClientDtoException exception = assertThrows(InvalidClientDtoException.class,
                () -> clientService.createClient(inputDto));

        assertTrue(exception.getMessage().contains("La fecha de nacimiento no puede ser futura"));
        verify(clientMapper, never()).insert(any());
    }


    @Test
    void createClient_WithDuplicatePhone_ShouldThrowException() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setId(1L);
        inputDto.setName("Test");
        inputDto.setFirstSurname("SurnameTest1");
        inputDto.setSecondSurname("SurnameTest2");
        inputDto.setPhone("+34123456789");
        inputDto.setEmail("test@example.com");

        when(clientMapper.existsById(inputDto.getId())).thenReturn(true);
        when(clientMapper.existsByNif(inputDto.getNif())).thenReturn(1);
        when(clientMapper.existsByEmail(inputDto.getEmail())).thenReturn(1);
        when(clientMapper.existsByPhone(inputDto.getPhone())).thenReturn(2);


        // When & Then
        DuplicateModel exception = assertThrows(DuplicateModel.class,
                () -> clientService.updateClient(inputDto));

        verifyNoInteractions(toEntity, toDto);
        assertTrue(exception.getMessage().contains("Ya existe un cliente con este teléfono: " + inputDto.getPhone()));
        verify(clientMapper, never()).updateClient(any());
    }
}