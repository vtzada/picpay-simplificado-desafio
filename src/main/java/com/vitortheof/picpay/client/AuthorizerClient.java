package com.vitortheof.picpay.client;

import com.vitortheof.picpay.client.dto.BaseResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authorizerClient", url = "https://util.devi.tools/api/v2/authorize")
public interface AuthorizerClient {

    @GetMapping
    ResponseEntity<BaseResponseDTO> isAuthorized();
}
