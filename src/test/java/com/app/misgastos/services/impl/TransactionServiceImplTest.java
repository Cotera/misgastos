package com.app.misgastos.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;



public class TransactionServiceImplTest {
    
    @Mock //en la clase que queremos emular
    private TransactionRepository transactionRepository;    

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setDescription("description");
        transactionDto.setAmount(1.2F);
        transactionDto.setType(TransactionTypeEnum.EXPEND);

        TransactionEntity transactionEntity=new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setDescription("description");
        transactionEntity.setAmount(1.2F);
        transactionEntity.setType(1);


        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);

        TransactionEntity result = transactionService.createTransaction(transactionDto);

        assertEquals(transactionEntity, result);        
    }

    @Test
    public void testDeleteById() { 

    }

    @Test
    public void testGetAll() {

    }

    @Test
    public void testGetById() { //metodo de test
        //give
        Long givenID = 1L;

        TransactionEntity foundEntity = new TransactionEntity();
        foundEntity.setId(givenID);
        foundEntity.setDescription("description");

        when(transactionRepository.findById(givenID)).thenReturn(Optional.of(foundEntity));

        TransactionDto expected = new TransactionDto();
        expected.setId(givenID);
        expected.setDescription("description");
        //when 

        Optional<TransactionDto> result = transactionService.getById(givenID); 
        


        // Then
        assertEquals( expected, result);


    }

    @Test
    public void testUpdate() {
        

    }
}
