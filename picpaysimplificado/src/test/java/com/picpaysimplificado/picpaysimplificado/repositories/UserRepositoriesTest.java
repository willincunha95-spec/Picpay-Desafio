package com.picpaysimplificado.picpaysimplificado.repositories;

import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.picpaysimplificado.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoriesTest {
    @Autowired
    UserRepositories userRepositories;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User sucessfully ftom DB")

    //Usando o metodo findUserByDocument no usuario que foi criado, e também dando caracteristicas a nosso usuarário
    void findUserByDocumentCase1 () {
        String document = "99999999981";
        UserDTO data = new UserDTO("Willian","Teste" ,document ,new BigDecimal(10) , "Teste123" , "Teste@gmail.com" ,  UserType.COMMON);
        this.createUser(data);

        Optional<User> foundUser =  this.userRepositories.findUserByDocument(document);
        //mostra se a consulta de usuário deu certo
        assertThat(foundUser.isPresent()).isTrue();

    }
    //Criando o usuario para testar a classe test
    private User createUser(UserDTO data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        this.entityManager.flush();
        return newUser;
    }
}