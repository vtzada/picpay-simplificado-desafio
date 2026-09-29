package com.vitortheof.picpay.service;

import com.vitortheof.picpay.dto.TransactionDTO;
import com.vitortheof.picpay.entity.Transaction;
import com.vitortheof.picpay.entity.User;
import com.vitortheof.picpay.entity.WalletType;
import com.vitortheof.picpay.exceptions.InsufficientBalanceException;
import com.vitortheof.picpay.exceptions.UserNotFoundException;
import com.vitortheof.picpay.exceptions.UnauthorizedTransactionException;
import com.vitortheof.picpay.repository.TransactionRepository;
import com.vitortheof.picpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    @Transactional
    public TransactionDTO transfer(TransactionDTO transaction) {

        User payer = userRepository.findById(transaction.payer())
                .orElseThrow(() -> new UserNotFoundException
                        ("Pagador não encontrado"));

        User payee = userRepository.findById(transaction.payee())
                .orElseThrow(() -> new UserNotFoundException
                        ("Recebedor não encontrado"));

        if (payee.getId().equals(payer.getId())) {
            throw new UnauthorizedTransactionException
                    ("Você não pode realizar transfêrencia para si mesmo!");
        }

        if (payer.getWalletType() == WalletType.COMMERCE) {
            throw new UnauthorizedTransactionException
                    ("Lojistas não estão autorizados a realizar transferências!");
        }

        if (payer.getBalance().compareTo(transaction.value()) < 0) {
            throw new InsufficientBalanceException
                    ("Saldo insuficiente para realizar a transferência!");
        }

        authorizationService.authorize();

        payer.setBalance(payer.getBalance().subtract(transaction.value()));
        payee.setBalance(payee.getBalance().add(transaction.value()));
        userRepository.save(payer);
        userRepository.save(payee);

        Transaction newTransaction = Transaction.builder()
                .sender(payer)
                .receiver(payee)
                .amount(transaction.value())
                .build();

        Transaction saved = transactionRepository.save(newTransaction);

        notificationService.notify(payer, payee, transaction.value());

        return new TransactionDTO(
                saved.getAmount(),
                payer.getId(),
                payee.getId()
        );
    }
}
