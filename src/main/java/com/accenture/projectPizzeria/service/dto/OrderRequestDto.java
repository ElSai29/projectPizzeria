package com.accenture.projectPizzeria.service.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderRequestDto(

        @NotBlank
        int orderPrice
) {
}
