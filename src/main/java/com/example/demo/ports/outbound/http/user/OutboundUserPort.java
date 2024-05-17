package com.example.demo.ports.outbound.http.user;

import com.example.demo.ports.outbound.http.user.dto.OutboundUserDTO;

public class OutboundUserPort {

    public OutboundUserDTO getUserById(int id) {
        // Simulate fetching user data
        return new OutboundUserDTO(id, "User " + id);
    }
}