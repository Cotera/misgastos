package com.app.misgastos.services.impl;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;
import com.app.misgastos.services.AccountService;
import com.app.misgastos.services.TransactionService;
import com.app.misgastos.utils.ExceptionCodesConstants;
import com.app.misgastos.utils.converters.TransactionConverter;
import com.app.misgastos.utils.exception.DataValidationException;
import com.app.misgastos.utils.exception.UnknownDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) throws DataValidationException {
        if (isNull(transactionDto) || nonNull(transactionDto.getId())) {
            throw new DataValidationException(ExceptionCodesConstants.VALIDATION_EXCEPTION_TRANSACTION);
        }
        TransactionEntity transactionEntity = TransactionConverter.toEntity(transactionDto);
        return TransactionConverter.toDto(transactionRepository.save(transactionEntity));
    }

    @Override
    public Optional<TransactionDto> getById(Long id) {
        Optional<TransactionEntity> transactionEntityOpt = transactionRepository.findById(id);

        if (transactionEntityOpt.isPresent()) {
            TransactionDto transactionDto = TransactionConverter.toDto(transactionEntityOpt.get());

            Long accountId = transactionEntityOpt.get().getAccount();
            if (nonNull(accountId)) {
                AccountDto account = accountService.getById(accountId);
                transactionDto.setAccountDto(account);
            }

            return Optional.of(transactionDto);
        }

        return Optional.empty();
    }

    @Override
    public List<TransactionDto> getAll() {
        return TransactionConverter.toDtos(transactionRepository.findAll());
    }

    @Override
    public Long deleteById(Long id) throws UnknownDataException {
        Optional<TransactionDto> transaccionEncontrada = this.getById(id);
        if (transaccionEncontrada.isEmpty()) {
            throw new UnknownDataException(ExceptionCodesConstants.TRANSACTION_DOES_NOT_EXISTS);
        }
        transactionRepository.deleteById(id);
        return id;
    }

    @Override
    public TransactionDto update(Long id, TransactionDto transactionDto) throws DataValidationException, UnknownDataException {
        this.validateInputData(id, transactionDto);
        TransactionDto existentTransaction = this.getById(id)
                .orElseThrow(() -> new UnknownDataException(ExceptionCodesConstants.TRANSACTION_DOES_NOT_EXISTS));

        return TransactionConverter.toDto(
                transactionRepository.save(this.setUpdateData(transactionDto, existentTransaction)));
    }

    private void validateInputData(Long id, TransactionDto transactionDto) throws DataValidationException {
        if (isNull(id) || id <= 0) {
            throw new DataValidationException(ExceptionCodesConstants.VALIDATION_EXCEPTION_ID);
        }
        if (isNull(transactionDto)) {
            throw new DataValidationException(ExceptionCodesConstants.VALIDATION_EXCEPTION_TRANSACTION);
        }
        if (nonNull(transactionDto.getId()) && transactionDto.getId() != id) {
            throw new DataValidationException(ExceptionCodesConstants.VALIDATION_EXCEPTION_ID);
        }
    }

    private TransactionEntity setUpdateData(TransactionDto newData, TransactionDto prevData) {
        TransactionEntity saveTransaction = new TransactionEntity();
        saveTransaction.setId(prevData.getId());
        if (nonNull(newData.getDescription())) {
            saveTransaction.setDescription(newData.getDescription());
        } else {
            saveTransaction.setDescription(prevData.getDescription());
        }

        if (nonNull(newData.getAmount())) {
            saveTransaction.setAmount(newData.getAmount());
        } else {
            saveTransaction.setAmount(prevData.getAmount());
        }

        if (nonNull(newData.getType())) {
            saveTransaction.setType(newData.getType().getId());
        } else {
            saveTransaction.setType(prevData.getType().getId());
        }

        if (nonNull(newData.getAccountDto())) {
            saveTransaction.setAccount(newData.getAccountDto().getId());
        } else {
            saveTransaction.setAccount(prevData.getAccountDto().getId());
        }
        return saveTransaction;
    }

    @Override
    public List<TransactionDto> getAllTransactionsByAccount(Long accountId) {
        return null;//TransactionConverter.toDtos(transactionRepository.getByAccountId(accountId));
    }
}