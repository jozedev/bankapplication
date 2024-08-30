package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.exception.NotAvailableBalanceException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        return transactionRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        return mapToDto(transactionRepository.getOne(id));
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction
        Account account = accountRepository.getOne(transactionDto.getAccountId());
        account.setInitialAmount(account.getInitialAmount() + transactionDto.getAmount());

        if (account.getInitialAmount() < 0) {
            throw new NotAvailableBalanceException("The account doesn't have enough money for the transaction.");
        }

        accountRepository.save(account);

        return mapToDto(transactionRepository.save(mapFromDto(transactionDto)));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report
        Account account = accountRepository.findByClientId(clientId);
        return transactionRepository.findByAccountId(clientId, dateTransactionStart, dateTransactionEnd)
                .stream().map(t -> this.mapToBankStatementDto(account, t)).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

    private Transaction mapFromDto(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setDate(transactionDto.getDate());
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setAccountId(transactionDto.getAccountId());

        return transaction;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getDate(), transaction.getType(),
                transaction.getAmount(), transaction.getBalance(), transaction.getAccountId());
    }

    private BankStatementDto mapToBankStatementDto(Account account, Transaction transaction) {
        return new BankStatementDto(transaction.getDate(), "client", account.getNumber(), account.getType(),
                account.getInitialAmount(), account.isActive(), transaction.getType(), transaction.getAmount(),
                transaction.getBalance());
    }
}
