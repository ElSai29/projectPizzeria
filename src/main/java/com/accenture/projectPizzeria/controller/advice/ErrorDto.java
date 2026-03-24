package com.accenture.projectPizzeria.controller.advice;

import java.time.LocalDateTime;

public record ErrorDto(LocalDateTime timestamp, int errorCode, String errorMessage) {
}
