package com.jozedev.bankapplication.backend.account.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.jozedev.bankapplication.backend.account.model.dto.BankStatementDto;
import com.jozedev.bankapplication.backend.account.model.dto.TransactionDto;

public interface TransactionService {

    public List<TransactionDto> getAll();
	public TransactionDto getById(Long id);
	public TransactionDto create(TransactionDto transactionDto);
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, @Param("dateTransactionStart") LocalDateTime dateTransactionStart, @Param("dateTransactionEnd") LocalDateTime dateTransactionEnd);
    public TransactionDto getLastByAccountId(Long accountId);
}
