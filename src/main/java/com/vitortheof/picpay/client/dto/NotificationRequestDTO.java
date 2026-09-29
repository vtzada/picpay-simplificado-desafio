package com.vitortheof.picpay.client.dto;

import java.math.BigDecimal;

public record NotificationRequestDTO(
        String senderName,
        String recipientName,
        BigDecimal amount
) {
}
