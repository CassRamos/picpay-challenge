package com.ms.spring_picpay.services;

import com.ms.spring_picpay.domain.user.User;
import com.ms.spring_picpay.domain.user.UserType;
import com.ms.spring_picpay.dtos.TransactionDTO;
import com.ms.spring_picpay.repositores.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionSuccessfully() throws Exception {
        var sender = new User(1L, "Cassiru", "Ramu", "4428922", "test@mail.com", "528234", new BigDecimal(190), UserType.COMMON);
        var receiver = new User(2L, "Lula", "Molusco", "442892200", "test1@mail.com", "528234", new BigDecimal(100), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(150), 1L, 2L);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        // Verify sender's balance after transaction
        BigDecimal expectedSenderBalance = new BigDecimal(40);
        assertEquals(expectedSenderBalance, sender.getBalance());
        verify(userService, times(1)).saveUser(sender);

        // Verify receiver's balance after transaction
        BigDecimal expectedReceiverBalance = new BigDecimal(250);
        assertEquals(expectedReceiverBalance, receiver.getBalance());
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transaction successfully completed");
        verify(notificationService, times(1)).sendNotification(receiver, "Transaction successfully received");
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionFailed() throws Exception {
        var sender = new User(1L, "Cassiru", "Ramu", "4428922", "test@mail.com", "528234", new BigDecimal(190), UserType.COMMON);
        var receiver = new User(2L, "Lula", "Molusco", "442892200", "test1@mail.com", "528234", new BigDecimal(100), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(150), 1L, 2L);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transaction not authorized", thrown.getMessage());
    }
}