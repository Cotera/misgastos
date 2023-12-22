package com.app.misgastos.services;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class AccountAnalysisService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    public AccountDto getAccountDataComplete(Long id) throws Exception {
        if (isNull(id)) {
            throw new Exception("Id de cuenta no válido");
        }
        AccountDto account = accountService.getById(id);
        List<TransactionDto> transactions = transactionService.getAllTransactionsByAccount(id);
        account.setTransactions(transactions);
        return account;
    }

    public AccountDto getAccountDataComplete(AccountDto account) throws Exception {
        if (isNull(account)) {
            throw new Exception("Cuenta no válida");
        }
        return this.getAccountDataComplete(account.getId());
    }
}
