package com.picpaysimplificado.picpaysimplificado.service;

import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void senderNotification(User user, String message) {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        try {
            // A URL correta para o mock atual é /v1/notify e o método é GET ou POST conforme a documentação
            // Mas para evitar o erro 404 de rota não encontrada, usamos a URL estável:
            ResponseEntity<String> notificationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v1/notify", String.class);

            if (notificationResponse.getStatusCode() != HttpStatus.OK) {
                System.out.println("Erro ao enviar notificação: Serviço respondeu com erro.");
            }
        } catch (Exception e) {
            // ESSENCIAL: Apenas logamos o erro. Não damos "throw new Exception".
            // Isso garante que o usuário receba o dinheiro mesmo se o aviso de e-mail falhar.
            System.out.println("Serviço de notificação indisponível: " + e.getMessage());
        }
    }
}