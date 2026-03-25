package com.accenture.projectPizzeria.service.dto;



import java.util.List;
import java.util.UUID;

public record CustomerResponseDto(

        UUID id,

    String name,

    String email,

    boolean isVip,

        List<OrderResponseDto> previousOrder
) {

}
