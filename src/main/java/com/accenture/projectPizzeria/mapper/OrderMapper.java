package com.accenture.projectPizzeria.mapper;

import com.accenture.projectPizzeria.repository.model.Order;
import com.accenture.projectPizzeria.service.dto.OrderRequestDto;
import com.accenture.projectPizzeria.service.dto.OrderResponseDto;

public interface OrderMapper {

    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);
}
