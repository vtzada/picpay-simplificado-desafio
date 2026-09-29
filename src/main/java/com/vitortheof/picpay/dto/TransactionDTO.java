package com.vitortheof.picpay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionDTO(
        @Positive
        BigDecimal value,
        @NotNull(message = "O id do pagador é obrigatório.")
        Long payer,
        @NotNull(message = "O id do recebedor é obrigatório.")
        Long payee
) {
}
