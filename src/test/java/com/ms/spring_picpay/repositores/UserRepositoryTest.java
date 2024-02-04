package com.ms.spring_picpay.repositores;

import com.ms.spring_picpay.domain.user.User;
import com.ms.spring_picpay.domain.user.UserType;
import com.ms.spring_picpay.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    void findUserByDocumentSuccess() {
        String doc = "48764548487";
        var data = new UserDTO("Cassiru", "Ramu", doc, "test@mail.com", "528234", new BigDecimal(190), UserType.COMMON);
        this.createUser(data);

        Optional<User> result = this.userRepository.findUserByDocument(doc);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from DB when user not exists")
    void findUserByDocumentFailed() {
        String doc = "48764548487";

        Optional<User> result = this.userRepository.findUserByDocument(doc);
        assertThat(result.isPresent()).isFalse();
    }

    private User createUser(UserDTO data) {
        var newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}