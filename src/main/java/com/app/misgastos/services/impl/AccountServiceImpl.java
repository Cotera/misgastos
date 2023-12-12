package com.app.misgastos.services.impl;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.entities.AccountEntity;
import com.app.misgastos.repository.AccountRepository;
import com.app.misgastos.services.AccountService;
import com.app.misgastos.utils.converters.AccountConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<AccountDto> createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountConverter.toEntity(accountDto);
        return Optional.ofNullable(
                AccountConverter.toDto(accountRepository.save(accountEntity))
        );
    }

    @Override
    public AccountDto getById(Long id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElse(null);
        return AccountConverter.toDto(accountEntity);
    }

    @Override
    public List<AccountDto> getAll() {
        List<AccountEntity> accountEntities = accountRepository.findAll();
        return AccountConverter.toDtos(accountEntities);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (accountRepository.findById(id).isEmpty()) {
            throw new Exception("Id no encontrado " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto update(Long id, AccountDto accountDto) throws Exception {
        if (Objects.isNull(id) || Objects.isNull(accountDto)) {
            throw new Exception("Datos no encontrados para actualizar.");
        }
        AccountEntity existentAccount = accountRepository.findById(id)
                .orElseThrow(() -> new Exception("El id que se quiere actalizar no existe: " + id));
        existentAccount.setName(accountDto.getName());
        if (Objects.nonNull(accountDto.getCurrency())) {
            existentAccount.setCurrency(accountDto.getCurrency().getCurrencyCode());
        }
        return AccountConverter.toDto(accountRepository.save(existentAccount));
    }

    private void asistenteDeUpdate() {

    }
}
