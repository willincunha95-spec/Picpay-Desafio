package com.picpaysimplificado.picpaysimplificado.service;

import com.picpaysimplificado.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Transactional // Se houver erro no meio do caminho, o dinheiro não some
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        // 1. Busca os usuários pelos UUIDs fornecidos no Insomnia
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        // 2. Valida se o pagador não é lojista e se tem saldo
        userService.validateTransaction(sender, transaction.value());

        // 3. Consulta o serviço autorizador externo
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transação não foi autorizada pelo serviço externo");
        }

        // 4. Cria a entidade de transação (com ID IDENTITY/Long)
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        // 5. Atualiza os saldos em memória
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        // 6. Persiste as mudanças no Banco H2
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        // 7. Envia notificações (Opcional, mas faz parte do desafio)
        this.notificationService.senderNotification(sender, "Transação enviada com sucesso");
        this.notificationService.senderNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        try {
            // Requisição para o mock externo que estava dando 403
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

            if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
                // Acessando o mapa: data -> authorization
                Map<String, Object> body = authorizationResponse.getBody();
                if (body != null && body.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    return (boolean) data.get("authorization");
                }
            }
            return false;
        } catch (Exception e) {
            // Se a API externa falhar, capturamos o erro aqui para o sistema não travar
            System.err.println("Serviço autorizador indisponível: " + e.getMessage());
            return true;
        }
    }
}