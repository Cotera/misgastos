package com.app.misgastos.services;

import com.app.misgastos.model.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    void createTransaction(TransactionDto transactionDto);

    Optional<TransactionDto> getById(Long id);

    List<TransactionDto> getAll();

    Long deleteById(Long id);

    /**
     * Uptade of an Annotation into Database
     * @param id id of element to update
     * @param transactionDto new data to update
     * @return updated element
     */
    TransactionDto update(Long id, TransactionDto transactionDto);

}
