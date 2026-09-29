package com.vitortheof.picpay.client.dto;

public record BaseResponseDTO(
        String status,
        AuthorizerResponseDTO data
) {
}
