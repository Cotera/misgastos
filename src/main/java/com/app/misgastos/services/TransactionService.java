package com.app.misgastos.services;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.utils.exception.DataValidationException;
import com.app.misgastos.utils.exception.UnknownDataException;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto) throws DataValidationException;

    Optional<TransactionDto> getById(Long id);

    List<TransactionDto> getAll();

    Long deleteById(Long id) throws UnknownDataException;

    /**
     * Uptade of an Annotation into Database
     * @param id id of element to update
     * @param transactionDto new data to update
     * @return updated element
     */
    TransactionDto update(Long id, TransactionDto transactionDto) throws DataValidationException, UnknownDataException;

    List<TransactionDto> getAllTransactionsByAccount(Long accountId);

}
