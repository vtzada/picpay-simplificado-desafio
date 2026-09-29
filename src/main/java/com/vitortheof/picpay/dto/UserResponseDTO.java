package com.vitortheof.picpay.dto;

import com.vitortheof.picpay.entity.WalletType;

import java.math.BigDecimal;

public record UserResponseDTO(
        String name,
        String email,
        String cpfCnpj,
        WalletType type,
        BigDecimal balance
) {
}
