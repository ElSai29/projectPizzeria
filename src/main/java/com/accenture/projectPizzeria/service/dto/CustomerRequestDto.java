package com.accenture.projectPizzeria.service.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDto(

        @NotBlank(message = "customer.name.null")
        String name,
        @NotBlank(message = "customer.email.null")
        String email


) {
}
