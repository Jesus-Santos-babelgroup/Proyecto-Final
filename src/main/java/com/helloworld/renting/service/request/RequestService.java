package com.helloworld.renting.service.request;

import com.helloworld.renting.dto.RentingRequestDto;
import com.helloworld.renting.entities.Client;
import com.helloworld.renting.entities.RentingRequest;
import com.helloworld.renting.exceptions.attributes.InvalidRequestDtoException;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import com.helloworld.renting.mapper.ClientMapper;
import com.helloworld.renting.mapper.MapStructRequest;
import com.helloworld.renting.mapper.RequestMapper;
import com.helloworld.renting.service.request.approval.ApprovalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestMapper requestMapper;
    private final ClientMapper clientMapper;
    private final MapStructRequest mapStruct;
    private final ApprovalService approvalService;

    public RequestService(RequestMapper requestMapper,
                          ClientMapper clientMapper,
                          MapStructRequest mapStruct,
                          ApprovalService approvalService) {

        this.requestMapper = requestMapper;
        this.clientMapper = clientMapper;
        this.mapStruct = mapStruct;
        this.approvalService = approvalService;
    }

    public RentingRequestDto create(RentingRequestDto requestDto) {

        validateDto(requestDto);
        requestDto.setPreResultType(approvalService.evaluate(requestDto));

        RentingRequest rentingRequest = mapStruct.toEntity(requestDto);
        Long requestId = requestMapper.insert(rentingRequest);
        requestDto.setId(requestId);

        return requestDto;
    }

    public List<RentingRequestDto> getByClientId(Long clientId) {
        List<RentingRequest> requestList = requestMapper.findByClientId(clientId);
        if (requestList == null || requestList.isEmpty()) {
            throw new NotFoundException("Requests not found for the given client ID.");
        }

        List<RentingRequestDto> requestDtoList = new ArrayList<>();
        for (RentingRequest request : requestList) {
            requestDtoList.add(mapStruct.toDto(request));
        }

        return requestDtoList;
    }

    public List<RentingRequestDto> getAll() {
        List<RentingRequest> requestsList = requestMapper.getAll();
        List<RentingRequestDto> requestDtosList = new ArrayList<>();
        for (RentingRequest r : requestsList) {
            requestDtosList.add(mapStruct.toDto(r));
        }
        return requestDtosList;
    }

    private void validateDto(RentingRequestDto dto) {
        Client client = clientMapper.findById(dto.getClient().getId());
        if (client == null) {
            throw new InvalidRequestDtoException("There is no client with the given ID.");
        }

        if (dto.getStartDate().isAfter(LocalDate.now())) {
            throw new InvalidRequestDtoException("Invalid value for start date. It cannot be set in the future.");
        }
    }
}