package com.ms.spring_picpay.services;

import com.ms.spring_picpay.domain.user.User;
import com.ms.spring_picpay.dtos.NotificationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        var notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate
                .postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6",
                        notificationRequest,
                        String.class);

        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
            System.out.println("Error on notification invite");
            throw new Exception("Notification service is offline");
        }
    }
}
