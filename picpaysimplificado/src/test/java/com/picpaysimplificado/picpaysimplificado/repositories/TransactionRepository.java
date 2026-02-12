package com.picpaysimplificado.picpaysimplificado.repositories;

import com.picpaysimplificado.picpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction , UUID> {
}
