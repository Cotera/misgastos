package com.app.misgastos.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;


@RunWith(SpringRunner.class)
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
    public void testDeleteById_returnsDeletedId_whenTransactionExists() throws Exception {
        // arrange
        Long givendId = 1L;
        TransactionEntity transactionFound = new TransactionEntity();
        transactionFound.setId(1L);
        transactionFound.setType(1);
        transactionFound.setAmount(3.6F);
        transactionFound.setDescription("Description");

        when(transactionRepository.findById(givendId)).thenReturn(Optional.of(transactionFound));
        doNothing().when(transactionRepository).deleteById(givendId);
        // test 
        Long result = transactionService.deleteById(givendId);
        
        // assert
        assertEquals(givendId, result);
        verify(transactionRepository, times(1)).deleteById(givendId);
    }

    @Test(expected = Exception.class)
    public void testDeleteById_throwsException_whenTransacitionNotExists() throws Exception {
        // arrange
        Long givendId = 1L;

        when(transactionRepository.findById(givendId)).thenReturn(Optional.empty());

        // test 
        transactionService.deleteById(givendId);
        // assert
        verify(transactionRepository, times(1)).findById(givendId);
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void testGetAll() {
        List<TransactionEntity> transactionEntityList = new ArrayList<>();
        TransactionEntity transaction1 = new TransactionEntity();
        transaction1.setId(1L);
        transaction1.setDescription("description");
        transaction1.setAmount(1.6F);
        transaction1.setType(1);

         TransactionEntity transaction2 = new TransactionEntity();
        transaction2.setId(2L);
        transaction2.setDescription("description 2");
        transaction2.setAmount(1.8F);
        transaction2.setType(1);

        transactionEntityList.add(transaction1);
        transactionEntityList.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactionEntityList);

        List<TransactionEntity> result = transactionService.getAll();

        assertEquals(transactionEntityList, result);
    }

    @Test
    public void testGetById() {
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
        assertEquals( expected, result.get());
    }

    @Test
    public void testUpdate() {
        

    }
}
