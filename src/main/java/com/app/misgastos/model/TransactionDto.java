package com.app.misgastos.model;

import lombok.Data;

@Data
public class TransactionDto {

    private Long id;
    private String description;
    private Float amount;

    private TransactionTypeEnum type;

}
