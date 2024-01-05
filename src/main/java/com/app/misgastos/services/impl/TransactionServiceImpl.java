package com.app.misgastos.services.impl;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;
import com.app.misgastos.services.AccountService;
import com.app.misgastos.services.TransactionService;
import com.app.misgastos.utils.converters.TransactionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) throws Exception {
        if (isNull(transactionDto)) {
            throw new Exception("Datos incorrectos");
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
            AccountDto account = accountService.getById(accountId);
            transactionDto.setAccountDto(account);

            return Optional.of(transactionDto);
        }

        return Optional.empty();
    }

    @Override
    public List<TransactionDto> getAll() {
        return TransactionConverter.toDtos(transactionRepository.findAll());
    }

    @Override
    public Long deleteById(Long id) throws Exception {
        Optional<TransactionDto> transaccionEncontrada = this.getById(id);
        if (transaccionEncontrada.isEmpty()) {
            throw new Exception("No existe transacción " + id);
        }
        transactionRepository.deleteById(id);
        return id;
    }

    @Override
    public TransactionDto update(Long id, TransactionDto transactionDto) throws Exception {
        if (isNull(id) || isNull(transactionDto)) {
            throw new Exception("No hay datos válidos para actualizar.");
        }
        this.getById(id)
                .orElseThrow(() -> new Exception("Error al Actualizar. No existe la transacción " + id));

        TransactionEntity saveTransaction = TransactionConverter.toEntity(transactionDto);
        saveTransaction.setId(id);

        return TransactionConverter.toDto(transactionRepository.save(saveTransaction));
    }

    @Override
    public List<TransactionDto> getAllTransactionsByAccount(Long accountId) {
        return TransactionConverter.toDtos(transactionRepository.getByAccountId(accountId));
    }
}