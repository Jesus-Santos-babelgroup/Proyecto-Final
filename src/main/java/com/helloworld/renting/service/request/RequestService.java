package com.helloworld.renting.service.request;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.RentingRequest;
import com.helloworld.renting.exceptions.attributes.InvalidRequestDtoException;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.RequestMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestMapper requestMapper;
    private final ClientMapper clientMapper;

    public RequestService(RequestMapper requestMapper, ClientMapper clientMapper) {
        this.requestMapper = requestMapper;
        this.clientMapper = clientMapper;
    }

    public RentingRequestDto create(RentingRequestDto requestDto) {

        if (!validDto(requestDto)) {
            throw new InvalidRequestDtoException("DTO is incompatible with the system.");
        }

//        RentingRequest rentingRequest = toEntity.toEntity(requestDto);
//        Integer requestId = requestMapper.insert(rentingRequest);
//        requestDto.setId(requestId);
        return requestDto;
    }

    public RentingRequestDto getById(Long requestId) {
        RentingRequest request = requestMapper.findById(requestId);
        if (request == null) {
            throw new NotFoundException("Request not found from the given ID.");
        }

        // map to dto
        return null;
    }

    public List<RentingRequestDto> getAll() {
        List<RentingRequest> requestsList = requestMapper.getAll();
        List<RentingRequestDto> requestDtosList = new ArrayList<>();
        for (RentingRequest r : requestsList) {
            // map list to dtosList
        }
        return requestDtosList;
    }

    private boolean validDto(RentingRequestDto dto) {
        Client client = clientMapper.findById(dto.getClientId());
        if (client == null) {
            return false;
        }

        return true;
    }
}