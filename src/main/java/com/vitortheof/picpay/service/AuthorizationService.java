package com.vitortheof.picpay.service;

import com.vitortheof.picpay.client.AuthorizerClient;
import com.vitortheof.picpay.client.dto.BaseResponseDTO;
import com.vitortheof.picpay.exceptions.AuthorizationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    private final AuthorizerClient authorizerClient;

    public void authorize() {
        ResponseEntity<BaseResponseDTO> response;
        try {
            response = authorizerClient.isAuthorized();
        } catch (FeignException e) {
            log.error("Falha ao consultar autorizador", e);
            throw new AuthorizationException("Falha ao consultar autorizador");
        }

        var body = response.getBody();
        if (!response.getStatusCode().is2xxSuccessful() || body == null) {
            throw new AuthorizationException("Autorizador retornou resposta inválida");
        }

        if (!body.data().authorization()) {
            throw new AuthorizationException("Transação não autorizada");
        }
    }
}
