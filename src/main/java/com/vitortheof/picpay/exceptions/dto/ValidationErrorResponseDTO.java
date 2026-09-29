package com.vitortheof.picpay.exceptions.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponseDTO(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String caminho,
        List<ErroCampoDTO> campos
) {

}
