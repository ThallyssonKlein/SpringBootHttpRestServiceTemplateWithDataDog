package com.example.demo.ports.outbound.http.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OutboundUserDTO {
    private int id;
    private String name;
}
