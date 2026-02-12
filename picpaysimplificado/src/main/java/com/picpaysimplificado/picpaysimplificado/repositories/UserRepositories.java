package com.picpaysimplificado.picpaysimplificado.repositories;

import com.picpaysimplificado.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositories extends JpaRepository<User, UUID> {

    Optional<User> findUserByDocument (String document);

    Optional<User> findUserById (UUID id);

}
