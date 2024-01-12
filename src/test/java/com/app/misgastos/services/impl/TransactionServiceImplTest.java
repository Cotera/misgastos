package com.app.misgastos.services.impl;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.entities.TransactionEntity;
import com.app.misgastos.repository.TransactionRepository;
import com.app.misgastos.services.AccountService;
import com.app.misgastos.utils.converters.TransactionConverter;

import com.app.misgastos.utils.exception.UnknownDataException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @Mock //en la clase que queremos emular
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionConverter transactionConverter;

    @Spy
    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setDescription("description");
        transactionDto.setAmount(1.2);
        transactionDto.setType(TransactionTypeEnum.EXPEND);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setDescription("description");
        transactionEntity.setAmount(1.2);
        transactionEntity.setType(1);
        
        TransactionDto transactionExpected = new TransactionDto();
        transactionExpected.setId(1L);
        transactionExpected.setDescription("description");
        transactionExpected.setAmount(1.2);
        transactionExpected.setType(TransactionTypeEnum.EXPEND);

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);


        TransactionDto result = transactionService.createTransaction(transactionDto);

        assertEquals(transactionExpected, result);
    }

    @Test
    public void testDeleteById_returnsDeletedId_whenTransactionExists() throws Exception {
        // arrange
        Long givendId = 1L;

        TransactionDto transaccionEncontrada = new TransactionDto();
        transaccionEncontrada.setId(1L);
        transaccionEncontrada.setType(TransactionTypeEnum.EXPEND);
        transaccionEncontrada.setAmount(3.6);
        transaccionEncontrada.setDescription("Description");

        doReturn(Optional.of(transaccionEncontrada))
                .when(transactionService).getById(givendId);      

        doNothing().when(transactionRepository).deleteById(givendId);
        // test Exception
        Long result = transactionService.deleteById(givendId);

        // assert
        assertEquals(givendId, result);
        verify(transactionRepository, times(1)).deleteById(givendId);
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void testDeleteById_throwsException_whenTransacitionNotExists() throws Exception {
        // arrange
        Long givenId = 1L;

        doReturn(Optional.empty())
                .when(transactionService).getById(givenId);

        // test
        assertThrows(UnknownDataException.class, () -> transactionService.deleteById(givenId));

        // assert
        verifyNoInteractions(transactionRepository);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<TransactionEntity> transactionEntityList = new ArrayList<>();

        TransactionEntity transaction1 = new TransactionEntity();
        transaction1.setId(1L);
        transaction1.setDescription("description");
        transaction1.setAmount(1.6);
        transaction1.setType(1);

        TransactionEntity transaction2 = new TransactionEntity();
        transaction2.setId(1L);
        transaction2.setDescription("description");
        transaction2.setAmount(1.6);
        transaction2.setType(1);

        transactionEntityList.add(transaction1);
        transactionEntityList.add(transaction2);

        List<TransactionDto> transactionDtos = new ArrayList<>();
        
        TransactionDto transactionDto1 = new TransactionDto();
        transactionDto1.setId(1L);
        transactionDto1.setDescription("description");
        transactionDto1.setAmount(1.6);
        transactionDto1.setType(TransactionTypeEnum.EXPEND);
        transactionDto1.setAccountDto(null);

        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setId(1L);
        transactionDto2.setDescription("description");
        transactionDto2.setAmount(1.6);
        transactionDto2.setType(TransactionTypeEnum.EXPEND);
        transactionDto2.setAccountDto(null);

        transactionDtos.add(transactionDto1);
        transactionDtos.add(transactionDto1);

        when(transactionRepository.findAll()).thenReturn(transactionEntityList);

        // Test
        List<TransactionDto> result = transactionService.getAll();

        // Assert
        assertEquals(transactionDtos, result);
    }

    @Test
    public void testGetById() {

        //give
        Long givenID = 1L;

        TransactionEntity foundEntity = new TransactionEntity();
        foundEntity.setId(givenID);
        foundEntity.setDescription("description");
        foundEntity.setType(3);

        when(transactionRepository.findById(givenID)).thenReturn(Optional.of(foundEntity));

        TransactionDto expected = new TransactionDto();
        expected.setId(givenID);
        expected.setDescription("description");
        expected.setType(TransactionTypeEnum.TRANSFER);
        
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

        when(transactionRepository.findById(givenID)).thenReturn(Optional.empty());

        //when
        Optional<TransactionDto> result = transactionService.getById(givenID);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @Ignore
    public void testUpdate_returnsUpdatedTransaction_whenTransactionExistsWithGivenId() throws Exception {
        //arrange
        Long givenID = 1L;
        TransactionDto givenTransaction = new TransactionDto();
        givenTransaction.setDescription("Description");
        givenTransaction.setAmount(1.5);

        TransactionEntity transactionFound = new TransactionEntity();
        transactionFound.setId(1L);

        doReturn(Optional.of(transactionFound)).when(transactionService).getById(givenID);

        TransactionEntity transactionToSave = new TransactionEntity();
        transactionToSave.setId(givenID);
        transactionToSave.setDescription(givenTransaction.getDescription());
        transactionToSave.setAmount(givenTransaction.getAmount());

        
        doReturn(transactionToSave).when(transactionRepository).save(any(TransactionEntity.class));

        TransactionDto expected = new TransactionDto();
        expected.setId(givenID);
        expected.setDescription("Description");
        expected.setAmount(1.5);

        //test
        TransactionDto result = transactionService.update(givenID, givenTransaction);

        //assert
        assertEquals(expected, result);
    }

    @Test
    public void testUpdate_throwsException_whenTransactionNotExistsWithGivenId() throws Exception {
        //arrange
        Long givenId = 1L;
        TransactionDto givenTransaction = new TransactionDto();
        givenTransaction.setDescription("Description");
        givenTransaction.setAmount(1.5);
       
        doReturn(Optional.empty()).when(transactionService).getById(givenId);
        //test
        assertThrows(UnknownDataException.class, () -> transactionService.update(givenId, givenTransaction));

        //assert
        verifyNoInteractions(transactionRepository);
    }

}
