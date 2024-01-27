package com.ms.spring_picpay.services;

import com.ms.spring_picpay.domain.user.User;
import com.ms.spring_picpay.domain.user.UserType;
import com.ms.spring_picpay.repositores.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Merchant user type is not allowed to make a transaction.");
        }
        if (sender.getBalance().compareTo(amount) <= 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository
                .findUserById(id)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
