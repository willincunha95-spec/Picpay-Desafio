package com.picpaysimplificado.picpaysimplificado.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "users")
@Table  (name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id ;

    private String firstName;


    @Column(unique = true)
    private String document;

    private String email;

    private String Passoword;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;





}
