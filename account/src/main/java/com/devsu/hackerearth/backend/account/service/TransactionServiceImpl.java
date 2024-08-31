package com.devsu.hackerearth.backend.account.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.devsu.hackerearth.backend.account.exception.AccountNotActiveException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.exception.NotAvailableBalanceException;

import javax.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
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
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction
        AccountDto account = accountService.getById(transactionDto.getAccountId());
        account.setBalance(account.getBalance() + transactionDto.getAmount());

        if (!account.isActive()) {
            throw new AccountNotActiveException("The account isn't active.");
        }

        if (account.getBalance() < 0) {
            throw new NotAvailableBalanceException("The account doesn't have enough money for the transaction.");
        }

        accountService.update(account);

        return mapToDto(transactionRepository.save(mapFromDto(transactionDto)));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId,
                                                                        LocalDateTime dateTransactionStart,
                                                                        LocalDateTime dateTransactionEnd) {
        // Report
        AccountDto account = accountService.getById(clientId);
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
        transaction.setAccountId(transactionDto.getAccountId());

        return transaction;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getDate(), transaction.getType(),
                transaction.getAmount(), transaction.getAccountId());
    }

    private BankStatementDto mapToBankStatementDto(AccountDto account, Transaction transaction) {
        return new BankStatementDto(transaction.getDate(), "client", account.getNumber(), account.getType(),
                account.getInitialAmount(), account.isActive(), transaction.getType(), transaction.getAmount(),
                account.getBalance());
    }
}
