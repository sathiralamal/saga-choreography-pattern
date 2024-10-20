package org.example.paymentservice.repository;

import org.example.paymentservice.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepositiry extends JpaRepository<UserTransaction,Integer> {
}
