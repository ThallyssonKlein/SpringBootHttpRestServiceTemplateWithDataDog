package com.example.demo.adapters.inbound.http.user;

import com.example.demo.domain.user.entity.User;
import com.example.demo.ports.inbound.http.dto.InboundUserDTO;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.ports.inbound.http.errors.BadRequestException;
import com.example.demo.ports.inbound.http.errors.ForbiddenException;
import com.example.demo.ports.inbound.http.errors.InternalErrorException;
import com.example.demo.domain.user.errors.NotAllowedToBeFoundException;
import datadog.trace.api.Trace;

public class InboundUserAdapter {

    private final UserService userService;

    public InboundUserAdapter(UserService userService) {
        this.userService = userService;
    }

    private InboundUserDTO convertUserToInboundUserDTO(User user) {
        InboundUserDTO dto = new InboundUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }

    @Trace(operationName = "InboundUserAdapter.getUserById")
    public InboundUserDTO getUserById(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Missing id param");
        }

        int idNumber;
        try {
            idNumber = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Id is not a number");
        }

        try {
            User domainResponse = userService.getUserById(idNumber);
            return convertUserToInboundUserDTO(domainResponse);
        } catch (NotAllowedToBeFoundException e) {
            throw new ForbiddenException(e.getMessage());
        } catch (Exception e) {
            throw new InternalErrorException("Unexpected error getting user");
        }
    }
}
