package com.app.misgastos.model;

import lombok.Data;

import java.util.Currency;
import java.util.List;

@Data
public class AccountDto {
    private Long id;
    private String name;
    private Currency currency;
    private Double initialBalance;

    private List<TransactionDto> transactions;
}
