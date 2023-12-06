package com.app.misgastos.services;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    TransactionEntity createTransaction(TransactionDto transactionDto);

    Optional<TransactionDto> getById(Long id);

    List<TransactionEntity> getAll();

    Long deleteById(Long id) throws Exception;

    /**
     * Uptade of an Annotation into Database
     * @param id id of element to update
     * @param transactionDto new data to update
     * @return updated element
     */
    TransactionDto update(Long id, TransactionDto transactionDto);

}
