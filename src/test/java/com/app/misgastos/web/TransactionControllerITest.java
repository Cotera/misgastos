package com.app.misgastos.web;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerITest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void getById() throws Exception {
        Long givenId = 1L;

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setDescription("transaction1");

        when(transactionService.getById(givenId)).thenReturn(Optional.of(transactionDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/{id}", givenId))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(transactionDto)));

    }

    @Test
    public void getAll() throws Exception {
        TransactionDto transaction1 = new TransactionDto();
        transaction1.setId(1L);
        TransactionDto transaction2 = new TransactionDto();
        transaction2.setId(2L);

        List<TransactionDto> list = List.of(transaction1, transaction2);
        when(transactionService.getAll()).thenReturn(list);

        mockMvc.perform(get("/transaction"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    public void update() throws Exception {

        Long givenId = 1L;
        TransactionDto givenBody = new TransactionDto();
        givenBody.setDescription("transaction1");
        givenBody.setAmount(34.20);

        TransactionDto updated = new TransactionDto();
        updated.setId(1L);
        updated.setDescription("transaction1");
        updated.setAmount(34.20);

        when(transactionService.update(givenId, givenBody))
                .thenReturn(updated);

        mockMvc.perform(put("/transaction/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(Charset.defaultCharset())
                .content(objectMapper.writeValueAsString(givenBody)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updated)));
    }
}