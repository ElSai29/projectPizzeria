package com.accenture.projectPizzeria.controller.api;

import com.accenture.projectPizzeria.controller.advice.ErrorDto;
import com.accenture.projectPizzeria.repository.model.Customer;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Customers", description = "management API of customers")
@RequestMapping("/customers")
public interface CustomerApi {

    @Operation(summary = "Add a new customer")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "400", description = "Invalid customer",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))

    @PostMapping
    ResponseEntity<Void> addCustomer(@RequestBody CustomerRequestDto requestDto);
}
