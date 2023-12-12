package com.app.misgastos.services.impl;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


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
    @Ignore // TODO Arreglar test
    public void testCreateTransaction() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setDescription("description");
        transactionDto.setAmount(1.2F);
        transactionDto.setType(TransactionTypeEnum.EXPEND);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setDescription("description");
        transactionEntity.setAmount(1.2F);
        transactionEntity.setType(1);


        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        TransactionDto result = transactionService.createTransaction(transactionDto);

        assertEquals(transactionEntity, result);
    }

    @Test
    @Ignore // TODO Arreglar test
    public void testDeleteById_returnsDeletedId_whenTransactionExists() throws Exception {
        // arrange
        Long givendId = 1L;
        TransactionEntity transactionFound = new TransactionEntity();
        transactionFound.setId(1L);
        transactionFound.setType(1);
        transactionFound.setAmount(3.6F);
        transactionFound.setDescription("Description");

        when(transactionRepository.findById(same(givendId)))
                .thenReturn(Optional.of(transactionFound));

        doNothing().when(transactionRepository).deleteById(givendId);
        // test Exception
        Long result = transactionService.deleteById(givendId);

        // assert
        assertEquals(givendId, result);
        verify(transactionRepository, times(1)).deleteById(givendId);
        verify(transactionRepository, times(1)).findById(givendId);
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void testDeleteById_throwsException_whenTransacitionNotExists() throws Exception {
        // arrange
        Long givenId = 1L;

        when(transactionRepository.findById(givenId)).thenReturn(Optional.empty());

        // test
        Exception result = assertThrows(Exception.class, () -> transactionService.deleteById(givenId));

        // assert
        assertEquals("No existe transacción " + givenId, result.getMessage());
        verify(transactionRepository, times(1)).findById(givenId);
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    @Ignore // TODO Arreglar test
    public void testGetAll() {
        // Arrange
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

        // Test
        List<TransactionDto> result = transactionService.getAll();

        // Assert
        assertEquals(transactionEntityList, result);
    }

    @Test
    @Ignore // TODO Arreglar test
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
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void testGetById_returnsEmpty_whenTransactionNotFound() {
        //give
        Long givenID = 1L;

        TransactionEntity foundEntity = new TransactionEntity();
        foundEntity.setId(givenID);
        foundEntity.setDescription("description");

        when(transactionRepository.findById(givenID)).thenReturn(Optional.empty());

        //when
        Optional<TransactionDto> result = transactionService.getById(givenID);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @Ignore  // TODO Arreglar test
    public void testUpdate_returnsUpdatedTransaction_whenTransactionExistsWithGivenId() throws Exception {
        //arrange
        Long givenID = 1L;
        TransactionDto givenTransaction = new TransactionDto();
        givenTransaction.setDescription("Description");
        givenTransaction.setAmount(1.5F);
        givenTransaction.setType(TransactionTypeEnum.EXPEND);

        TransactionEntity transactionFound = new TransactionEntity();
        transactionFound.setId(1L);

        when(transactionRepository.findById(givenID)).thenReturn(Optional.of(transactionFound));

        TransactionEntity transactionToSave = new TransactionEntity();
        transactionToSave.setId(givenID);
        transactionToSave.setDescription(givenTransaction.getDescription());
        transactionToSave.setAmount(givenTransaction.getAmount());
        transactionToSave.setType(1);

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionToSave);
        //test
        TransactionDto result = transactionService.update(givenID, givenTransaction);

        //assert
        assertEquals(transactionToSave, result);
    }

    @Test
    @Ignore // TODO Arreglar test
    public void testUpdate_returnsUpdatedTransactionWithoutType_whenTransactionExistsWithGivenId() throws Exception {
        //arrange
        Long givenID = 1L;
        TransactionDto givenTransaction = new TransactionDto();
        givenTransaction.setDescription("Description");
        givenTransaction.setAmount(1.5F);

        TransactionEntity transactionFound = new TransactionEntity();
        transactionFound.setId(1L);

        when(transactionRepository.findById(givenID)).thenReturn(Optional.of(transactionFound));

        TransactionEntity transactionToSave = new TransactionEntity();
        transactionToSave.setId(givenID);
        transactionToSave.setDescription(givenTransaction.getDescription());
        transactionToSave.setAmount(givenTransaction.getAmount());

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionToSave);
        //test
        TransactionDto result = transactionService.update(givenID, givenTransaction);

        //assert
        assertEquals(transactionToSave, result);
    }

    @Test
    public void testUpdate_throwsException_whenTransactionNotExistsWithGivenId() throws Exception {
        //arrange
        Long givenId = 1L;
        TransactionDto givenTransaction = new TransactionDto();
        givenTransaction.setDescription("Description");
        givenTransaction.setAmount(1.5F);

        when(transactionRepository.findById(givenId)).thenReturn(Optional.empty());
        //test
        Exception result = assertThrows(Exception.class, () -> transactionService.update(givenId, givenTransaction));

        //assert
        assertEquals("Error al Actualizar. No existe la transacción " + givenId, result.getMessage());
        verify(transactionRepository, times(1)).findById(givenId);
        verifyNoMoreInteractions(transactionRepository);
    }

}
