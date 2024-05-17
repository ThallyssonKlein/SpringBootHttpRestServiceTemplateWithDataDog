package com.example.demo.ports.inbound.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import datadog.trace.api.Trace;
import com.example.demo.adapters.inbound.http.user.InboundUserAdapter;
import com.example.demo.ports.inbound.http.dto.InboundUserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    private final InboundUserAdapter inboundUserAdapter;

    @Autowired
    public UserController(InboundUserAdapter inboundUserAdapter) {
        this.inboundUserAdapter = inboundUserAdapter;
    }

    @GetMapping("/{id}")
    @Trace(operationName = "UserController.getUserById")
    public ResponseEntity<InboundUserDTO> getUserById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(inboundUserAdapter.getUserById(id));
    }
}

