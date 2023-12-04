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

        transactionEntity.setType(transactionDto.getType().getId());

        return transactionRepository.save(transactionEntity);
        
    }

    @Override
    public Optional<TransactionDto> getById(Long id) {  //metodo a probar 
        Optional<TransactionEntity> annotationEntityOpt = transactionRepository.findById(id);

        if (annotationEntityOpt.isPresent()) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(annotationEntityOpt.get().getId());
            transactionDto.setDescription(annotationEntityOpt.get().getDescription());
            transactionDto.setAmount(annotationEntityOpt.get().getAmount());
            transactionDto.setType(
                    TransactionTypeEnum.getFromId(
                            annotationEntityOpt.get().getType()
                    ));
            return Optional.of(transactionDto);
        }

        return Optional.empty();
    }

    @Override
    public List<TransactionDto> getAll() {
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public TransactionDto update(Long id, TransactionDto transactionDto) {
        return null;
    }
}
