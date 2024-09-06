package com.jozedev.bankapplication.backend.account.service;

import java.util.List;

import com.jozedev.bankapplication.backend.account.model.dto.AccountDto;
import com.jozedev.bankapplication.backend.account.model.dto.PartialAccountDto;

public interface AccountService {

    public List<AccountDto> getAll();
	public AccountDto getById(Long id);
	public AccountDto create(AccountDto accountDto);
	public AccountDto update(AccountDto accountDto);
	public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto);
	public void deleteById(Long id);
}
