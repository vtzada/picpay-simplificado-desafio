package com.vitortheof.picpay.exceptions;

import com.vitortheof.picpay.exceptions.dto.ErroCampoDTO;
import com.vitortheof.picpay.exceptions.dto.ErroResponseDTO;
import com.vitortheof.picpay.exceptions.dto.ValidationErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailAlreadyExistsException.class, CpfCnpjAlreadyExistsException.class})
    public ResponseEntity<ErroResponseDTO> recursoJaCadastrado(RuntimeException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> usuarioNaoEncontrado(UserNotFoundException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler({InsufficientBalanceException.class, UnauthorizedTransactionException.class, AuthorizationException.class})
    public ResponseEntity<ErroResponseDTO> transferenciaNaoPermitida(RuntimeException ex, HttpServletRequest request) {
        ErroResponseDTO erro = new ErroResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> tratarErroValidacao(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErroCampoDTO> camposComErro = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErroCampoDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        ValidationErrorResponseDTO erro = new ValidationErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos preenchidos",
                request.getRequestURI(),
                camposComErro
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}