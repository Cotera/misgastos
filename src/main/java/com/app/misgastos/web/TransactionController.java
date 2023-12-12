package com.app.misgastos.web;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.services.impl.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto){
        log.info("Creating object with id: " + transactionDto.getId());
        //transactionService.createTransaction(transactionDto);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable("id") Long id){
        log.info("Searching for object with id: " + id);
        Optional<TransactionDto> annotacion = transactionService.getById(id);

        if (annotacion.isPresent()) {
            return ResponseEntity.ok(annotacion.get());
        } else {
            log.error("Object not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
