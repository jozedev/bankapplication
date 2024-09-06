package com.jozedev.bankapplication.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jozedev.bankapplication.backend.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByClientId(Long clientId);
}
