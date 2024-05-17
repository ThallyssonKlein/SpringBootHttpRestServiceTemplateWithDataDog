package com.example.demo.domain.user.service;

import com.example.demo.domain.user.errors.NotAllowedToBeFoundException;
import com.example.demo.adapters.outbound.http.user.OutboundUserAdapter;
import com.example.demo.domain.user.entity.User;
import datadog.trace.api.Trace;

public class UserService {
    private final String spanName = "UserService.getUserById";
    private final OutboundUserAdapter outboundUserAdapter;

    public UserService(OutboundUserAdapter outboundUserAdapter) {
        this.outboundUserAdapter = outboundUserAdapter;
    }

    private boolean canGetUser(int userId) {
        return userId < 10 && userId > 0;
    }

    @Trace(operationName = "UserService.getUserById")
    public User getUserById(int id) throws Exception {
        if (canGetUser(id)) {
            return outboundUserAdapter.getUserById(id);
        } else {
            throw new NotAllowedToBeFoundException("User not allowed to be found");
        }
    }
}
