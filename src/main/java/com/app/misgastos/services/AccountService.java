package com.app.misgastos.services;

import com.app.misgastos.model.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> createAccount(AccountDto accountDto);

    Optional<AccountDto> getById(Long id);

    List<AccountDto> getAll();

    void deleteById(Long id) throws Exception;

    AccountDto update(Long id, AccountDto accountDto) throws Exception;
}
