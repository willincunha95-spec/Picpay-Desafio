package com.picpaysimplificado.picpaysimplificado.service;

import com.picpaysimplificado.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public Transaction createTransaction(TransactionDTO transaction)throws Exception{
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.ReceiverId());

        userService.validateTransaction(sender , transaction.value());

        boolean isAutorized = this.authorizeTransaction(sender , transaction.value());
        if(!isAutorized){
            throw new Exception("Transação não foi autorizada");
        }

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transaction.value());
        newtransaction.setSender(sender);
        newtransaction.setReceiver(receiver);
        newtransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newtransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        this.notificationService.senderNotification(sender , "Transação enviada com sucesso ");
        this.notificationService.senderNotification(receiver , "Transação recebida com sucesso ");


        return newtransaction;
    }


    public boolean authorizeTransaction(User sender , BigDecimal value){
       ResponseEntity<Map> authorizationResponse =  restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

      if(authorizationResponse.getStatusCode() == HttpStatus.OK ){
          String message =(String) authorizationResponse.getBody().get("message");
          return "Autorizado" .equalsIgnoreCase(message);

      } else return false;
    }
}
