package com.accenture.projectPizzeria.service.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CustomerResponseDto(

        UUID id,

    @NotBlank(message = "customer.name.null")
    String name,
    @NotBlank(message = "customer.email.null")
    String email,

    boolean isVip
) {

}
