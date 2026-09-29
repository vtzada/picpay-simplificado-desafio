package com.vitortheof.picpay.exceptions.dto;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String caminho
) {
}
