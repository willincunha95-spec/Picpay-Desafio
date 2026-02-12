package com.picpaysimplificado.picpaysimplificado.service;

import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.picpaysimplificado.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepositories repository;

    public void validateTransaction(User sender,  BigDecimal amount ) throws Exception {
        if(sender.getUserType() == UserType.COMMON){
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }
        if(sender.getBalance().compareTo(amount)<0){
            throw new Exception("Usuário não possui saldo suficiente para transação");

        }
    }
    public User findUserById(UUID id)throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não foi encontrado"));

    }

    public  User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){
        return this.repository.findAll();

    }

    public void saveUser (User user){
        this.repository.save(user);
    }
}
