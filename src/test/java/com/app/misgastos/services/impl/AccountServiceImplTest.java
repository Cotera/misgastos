package com.app.misgastos.services.impl;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.entities.AccountEntity;
import com.app.misgastos.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Currency;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;


    @Before
    public void inicio() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount() {
    }

    @Test
    public void getById() {
        // ARRANGE (Given)
        Long givenId = 1L;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setName("Cuenta Banco España");
        accountEntity.setCurrency("EUR");

        Optional<AccountEntity> accountEntityO = Optional.of(accountEntity);
        when(accountRepository.findById(givenId)).thenReturn(accountEntityO);

        // TEST (when)
        Optional<AccountDto> actualAccount = accountService.getById(givenId);

        // ASSERT (then)
        assertTrue(actualAccount.isPresent());
        assertEquals(1L, actualAccount.get().getId().longValue());
        assertEquals("Cuenta Banco España", actualAccount.get().getName());
        assertEquals(Currency.getInstance("EUR"), actualAccount.get().getCurrency());
    }

    @Test
    public void getAll() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void update() {
    }
}