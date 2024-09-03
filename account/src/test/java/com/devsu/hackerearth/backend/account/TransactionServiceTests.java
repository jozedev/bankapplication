package com.devsu.hackerearth.backend.account;

import com.devsu.hackerearth.backend.account.exception.AccountNotActiveException;
import com.devsu.hackerearth.backend.account.exception.NotAvailableBalanceException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.ServiceClient;
import com.devsu.hackerearth.backend.account.service.TransactionService;
import com.devsu.hackerearth.backend.account.service.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTests {

    private TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private AccountService accountService = mock(AccountService.class);
    private ServiceClient serviceClient = mock(ServiceClient.class);
    private TransactionService transactionService = new TransactionServiceImpl(transactionRepository,
            accountService, serviceClient);

    @Test
    @DisplayName("Test when account is not active it should throw an exception")
    void testAccountNotActive() {
        AccountDto account = new AccountDto(1L, "number", "savings", 1000, 1000,false, 1L);
        when(accountService.getById(1L)).thenReturn(account);

        TransactionDto transaction = new TransactionDto(1L, LocalDateTime.now(), "deposit", 10, 1L);

        Assertions.assertThrows(AccountNotActiveException.class, () -> transactionService.create(transaction));
    }

    @Test
    @DisplayName("Test when account not having enough money it should throw an exception")
    void testAccountNotHavingEnoughMoney() {
        AccountDto account = new AccountDto(1L, "number", "savings", 100, 100, true, 1L);
        when(accountService.getById(1L)).thenReturn(account);

        TransactionDto transaction = new TransactionDto(1L, LocalDateTime.now(), "deposit", -101, 1L);

        Assertions.assertThrows(NotAvailableBalanceException.class, () -> transactionService.create(transaction));
    }

    @Test
    @DisplayName("Test when account has enough money and is active it should go through")
    void testAccountHavingEnoughMoneyAndActive() {
        AccountDto account = new AccountDto(1L, "number", "savings", 100, 100, true, 1L);
        when(accountService.getById(1L)).thenReturn(account);

        LocalDateTime date = LocalDateTime.now();
        TransactionDto transactionDto = new TransactionDto(1L, date, "deposit", -99, 1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setDate(date);
        transaction.setType("deposit");
        transaction.setAmount(-99);
        transaction.setAccountId(1L);

        when(transactionRepository.save(any())).thenReturn(transaction);

        Assertions.assertEquals(transactionDto, transactionService.create(transactionDto));
    }

    @Test
    @DisplayName("Test that transactions are returned by date")
    void testGetAllByAccountClientIdAndDateBetween() {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setDate(LocalDateTime.of(2021, 1, 1, 10, 0, 0));
        transaction1.setType("deposit");
        transaction1.setAmount(50);
        transaction1.setAccountId(1L);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setDate(LocalDateTime.of(2021, 1, 2, 10, 0, 0));
        transaction2.setType("payment");
        transaction2.setAmount(-10);
        transaction2.setAccountId(1L);

        Transaction transaction3 = new Transaction();
        transaction3.setId(3L);
        transaction3.setDate(LocalDateTime.of(2021, 1, 3, 10, 0, 0));
        transaction3.setType("deposit");
        transaction3.setAmount(20);
        transaction3.setAccountId(1L);

        Transaction transaction4 = new Transaction();
        transaction4.setId(4L);
        transaction4.setDate(LocalDateTime.of(2021, 1, 4, 10, 0, 0));
        transaction4.setType("payment");
        transaction4.setAmount(-20);
        transaction4.setAccountId(1L);

        AccountDto account = new AccountDto(1L, "number", "savings", 100, 100,true, 1L);

        when(transactionRepository.findByAccountId(1L,
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 4, 23, 59, 59))).
                thenReturn(new ArrayList<>(List.of(transaction1, transaction2, transaction3, transaction4)));

        when(accountService.getById(1L)).thenReturn(account);

        List<BankStatementDto> listExpected = new ArrayList<>(List.of(
                new BankStatementDto(LocalDateTime.of(2021, 1, 1, 10, 0, 0), "client", "number", "savings", 100, true, "deposit", 50, 100),
                new BankStatementDto(LocalDateTime.of(2021, 1, 2, 10, 0, 0), "client", "number", "savings", 100, true, "payment", -10, 100),
                new BankStatementDto(LocalDateTime.of(2021, 1, 3, 10, 0, 0), "client", "number", "savings", 100, true, "deposit", 20, 100),
                new BankStatementDto(LocalDateTime.of(2021, 1, 4, 10, 0, 0), "client", "number", "savings", 100, true, "payment", -20, 100)));

        List<BankStatementDto> listReturned = transactionService.getAllByAccountClientIdAndDateBetween(1L,
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 4, 23, 59, 59));

        Assertions.assertArrayEquals(listExpected.toArray(), listReturned.toArray());
    }
}
