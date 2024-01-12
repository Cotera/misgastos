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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;

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
    public void getAll() throws Exception {
        TransactionDto transaction1 = new TransactionDto();
        transaction1.setId(1L);
        TransactionDto transaction2 = new TransactionDto();
        transaction2.setId(2L);

        List<TransactionDto> list = List.of(transaction1, transaction2);
        when(transactionService.getAll())
                .thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction"))
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

        when(transactionService.update(givenId, givenBody))
                .thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/transaction/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}