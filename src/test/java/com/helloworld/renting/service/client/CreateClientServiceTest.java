package com.helloworld.renting.service.client;

import com.helloworld.renting.dto.ClientDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.StructMapperToDto;
import com.helloworld.renting.mapper.StructMapperToEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClientServiceTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private StructMapperToDto toDto;

    @Mock
    private StructMapperToEntity toEntity;

    @InjectMocks
    private ClientService clientService;

    @Test
    void createClient_BasicFunctionality() {
        // Given
        ClientDto inputDto = new ClientDto();
        inputDto.setNif("12345678A");
        inputDto.setEmail("test@example.com");

        Client client = new Client();
        ClientDto outputDto = new ClientDto();
        outputDto.setId(1L);

        when(clientMapper.existsByNif(any())).thenReturn(false);
        when(clientMapper.existsByEmail(any())).thenReturn(false);
        when(toEntity.toEntity(any())).thenReturn(client);
        when(toDto.toDto(any())).thenReturn(outputDto);

        // When
        ClientDto result = clientService.createClient(inputDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}