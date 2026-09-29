package com.vitortheof.picpay.exceptions.dto;

public record ErroCampoDTO(
        String campo,
        String mensagem
) {
}
