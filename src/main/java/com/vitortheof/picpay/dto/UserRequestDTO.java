package com.vitortheof.picpay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "O nome completo é obrigatório.")
        String fullName,
        @Email(message = "O tipo de e-mail é inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password,
        @NotBlank(message = "O cpf/cnpj é obrigatório.")
        String cpfCnpj
) {
}

