package com.vitortheof.picpay.exceptions;

public class CpfCnpjAlreadyExistsException extends RuntimeException {
    public CpfCnpjAlreadyExistsException(String message) {
        super(message);
    }
}
