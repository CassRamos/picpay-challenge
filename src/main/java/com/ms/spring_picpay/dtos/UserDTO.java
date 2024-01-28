package com.ms.spring_picpay.dtos;

import com.ms.spring_picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName,
                      String lastName,
                      String document,
                      String email,
                      String password,
                      BigDecimal balance,
                      UserType userType) {
}
