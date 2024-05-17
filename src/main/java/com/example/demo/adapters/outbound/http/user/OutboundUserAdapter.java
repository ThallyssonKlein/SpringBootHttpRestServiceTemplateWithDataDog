package com.example.demo.adapters.outbound.http.user;

import com.example.demo.domain.user.entity.User;
import com.example.demo.ports.outbound.http.user.OutboundUserPort;
import com.example.demo.ports.outbound.http.user.dto.OutboundUserDTO;
import com.example.demo.ports.inbound.http.errors.InternalErrorException;
import datadog.trace.api.Trace;

public class OutboundUserAdapter {
    private final String spanName = "";
    private final OutboundUserPort userPort;

    public OutboundUserAdapter(OutboundUserPort userPort) {
        this.userPort = userPort;
    }

    private User convertOutboundIUserDTOToIUser(OutboundUserDTO user) {
        return new User(user.getId(), user.getName());
    }

    @Trace(operationName = "OutboundUserAdapter.getUserById")
    public User getUserById(int id) throws InternalErrorException {
        try {
            OutboundUserDTO outboundPortResponse = userPort.getUserById(id);
            return this.convertOutboundIUserDTOToIUser(outboundPortResponse);
        } catch (Exception e) {
            throw new InternalErrorException("Unexpected error getting user");
        }
    }
}
