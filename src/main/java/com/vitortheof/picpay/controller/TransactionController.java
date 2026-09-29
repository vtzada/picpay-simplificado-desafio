package com.vitortheof.picpay.controller;

import com.vitortheof.picpay.dto.TransactionDTO;
import com.vitortheof.picpay.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/transfer")
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransactionDTO transaction) {
        return ResponseEntity.ok(transactionService.transfer(transaction));
    }
}
