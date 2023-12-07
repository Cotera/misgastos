package com.app.misgastos.services.impl;

import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;
import com.app.misgastos.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionEntity createTransaction(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionDto.getId());
        transactionEntity.setDescription(transactionDto.getDescription());
        transactionEntity.setAmount(transactionDto.getAmount());

        if (nonNull(transactionDto.getType())) {
            transactionEntity.setType(transactionDto.getType().getId());
        }

        return transactionRepository.save(transactionEntity);
    }

    @Override
    public Optional<TransactionDto> getById(Long id) {  //metodo a probar
        Optional<TransactionEntity> transactionEntityOpt = transactionRepository.findById(id);

        if (transactionEntityOpt.isPresent()) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(transactionEntityOpt.get().getId());
            transactionDto.setDescription(transactionEntityOpt.get().getDescription());
            transactionDto.setAmount(transactionEntityOpt.get().getAmount());
            if ( nonNull(transactionEntityOpt.get().getType()) ) {
                transactionDto.setType(
                        TransactionTypeEnum.getFromId(
                                transactionEntityOpt.get().getType()
                        ));
            }
            return Optional.of(transactionDto);
        }

        return Optional.empty();
    }

    @Override
    public List<TransactionEntity> getAll() {
        return transactionRepository.findAll();
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
    public TransactionDto update(Long id, TransactionDto transactionDto) {
        Optional<TransactionDto> transaccionEncontrada = this.getById(id);

        transaccionEncontrada.ifPresent(this.actualizaTransaccion());


        return null;
    }

    private Consumer<? super TransactionDto> actualizaTransaccion() {
        return null;
    }
}
