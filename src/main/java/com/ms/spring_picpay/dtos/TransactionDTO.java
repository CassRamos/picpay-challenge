package com.ms.spring_picpay.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value,
                             Long senderId,
                             Long receiverId) {
}
