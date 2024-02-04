package com.ms.spring_picpay.services;

import com.ms.spring_picpay.domain.transaction.Transaction;
import com.ms.spring_picpay.domain.user.User;
import com.ms.spring_picpay.dtos.TransactionDTO;
import com.ms.spring_picpay.repositores.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    final UserService userService;
    final TransactionRepository transactionRepository;
    final NotificationService notificationService;
    final AuthorizationService authorizationService;

    final RestTemplate restTemplate; //spring class for consuming rest APIs

    public TransactionService(UserService userService, TransactionRepository transactionRepository, NotificationService notificationService, AuthorizationService authorizationService, RestTemplate restTemplate) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.restTemplate = restTemplate;
    }

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transaction not authorized");
        }

        var newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transaction successfully completed");
        this.notificationService.sendNotification(receiver, "Transaction successfully received");

        return newTransaction;
    }
}
