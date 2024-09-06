package com.jozedev.bankapplication.backend.account.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.jozedev.bankapplication.backend.account.exception.AccountNotActiveException;
import com.jozedev.bankapplication.backend.account.exception.ClientServiceException;
import com.jozedev.bankapplication.backend.account.model.dto.AccountDto;
import com.jozedev.bankapplication.backend.account.model.dto.ClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jozedev.bankapplication.backend.account.model.dto.BankStatementDto;
import com.jozedev.bankapplication.backend.account.model.dto.TransactionDto;
import com.jozedev.bankapplication.backend.account.repository.TransactionRepository;
import com.jozedev.bankapplication.backend.account.model.Transaction;
import com.jozedev.bankapplication.backend.account.exception.NotAvailableBalanceException;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ServiceClient serviceClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountService accountService,
                                  ServiceClient serviceClient) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.serviceClient = serviceClient;
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
        ResponseEntity<ClientDto> responseClient = serviceClient.getForEntity("client-info",
                ClientDto.class, clientId);
        if (responseClient.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ClientServiceException("No se encontró el cliente");
        }

        if (responseClient.getStatusCode() != HttpStatus.OK) {
            throw new ClientServiceException("No se pudo contactar con el servicio de clientes");
        }

        return transactionRepository.findByAccountId(clientId, dateTransactionStart, dateTransactionEnd)
                .stream().map(t -> this.mapToBankStatementDto(account, t, responseClient.getBody())).collect(Collectors.toList());
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

    private BankStatementDto mapToBankStatementDto(AccountDto account, Transaction transaction, ClientDto client) {
        return new BankStatementDto(transaction.getDate(), client.getName(), account.getNumber(), account.getType(),
                account.getInitialAmount(), account.isActive(), transaction.getType(), transaction.getAmount(),
                account.getBalance());
    }
}
