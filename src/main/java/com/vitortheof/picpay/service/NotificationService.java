package com.vitortheof.picpay.service;

import com.vitortheof.picpay.client.NotificationClient;
import com.vitortheof.picpay.client.dto.NotificationRequestDTO;
import com.vitortheof.picpay.entity.User;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationClient notificationClient;

    public void notify(User sender, User receiver, BigDecimal amount) {

        try {
            NotificationRequestDTO body = new NotificationRequestDTO(
                    sender.getFullName(),
                    receiver.getFullName(),
                    amount);
            notificationClient.notify(body);
        } catch (FeignException e) {
            log.error("Falha ao enviar notificação", e);
        }
    }
}
