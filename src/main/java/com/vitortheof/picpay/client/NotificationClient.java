package com.vitortheof.picpay.client;

import com.vitortheof.picpay.client.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationClient", url = "https://util.devi.tools/api/v1/notify")
public interface NotificationClient {

    @PostMapping
    ResponseEntity<Void> notify(@RequestBody NotificationRequestDTO body);
}
