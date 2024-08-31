package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
		return accountRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
		return mapToDto(accountRepository.getOne(id));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        accountDto.setBalance(accountDto.getInitialAmount());
		return mapToDto(accountRepository.save(mapFromDto(accountDto)));
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
		return mapToDto(accountRepository.save(mapFromDto(accountDto)));
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
        AccountDto accountDto = mapToDto(accountRepository.getOne(id));
        accountDto.setActive(partialAccountDto.isActive());
		return mapToDto(accountRepository.save(mapFromDto(accountDto)));
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
        accountRepository.deleteById(id);
    }
    
    private Account mapFromDto(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setBalance(accountDto.getBalance());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        return account;
    }
    
    private AccountDto mapToDto(Account account) {
        return new AccountDto(account.getId(), account.getNumber(), account.getType(), 
                account.getInitialAmount(), account.getBalance(), account.isActive(),
                account.getClientId());
    }
}
