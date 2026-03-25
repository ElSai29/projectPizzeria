package com.accenture.projectPizzeria.service.dto;

import com.accenture.projectPizzeria.enums.OrderStatus;

import java.util.UUID;

public record OrderResponseDto(

        UUID orderId,

        int orderPrice,

        OrderStatus orderStatus
) {
}
