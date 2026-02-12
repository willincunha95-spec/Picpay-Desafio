package com.picpaysimplificado.picpaysimplificado.dtos;

import com.picpaysimplificado.picpaysimplificado.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


public record TransactionDTO(BigDecimal value , UUID senderId , UUID ReceiverId) {

}
