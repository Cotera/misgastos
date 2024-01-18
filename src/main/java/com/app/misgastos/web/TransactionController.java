package com.app.misgastos.web;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.services.TransactionService;
import com.app.misgastos.utils.exception.DataValidationException;
import com.app.misgastos.utils.exception.UnknownDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
        log.info("Creating transaction");
        try {
            TransactionDto created = transactionService.createTransaction(transactionDto);
            return ResponseEntity.ok(created);
        } catch (DataValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable("id") Long id) {
        log.info("Searching for object with id: " + id);
        Optional<TransactionDto> annotacion = transactionService.getById(id);

        if (annotacion.isPresent()) {
            return ResponseEntity.ok(annotacion.get());
        } else {
            log.error("Object not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        List<TransactionDto> transactionDtos = transactionService.getAll();
        // return new ResponseEntity<>(transactionDtos, HttpStatus.OK);
        return ResponseEntity.ok(transactionDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionDto> delete(@PathVariable("id") Long id) {
        try {
            transactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownDataException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> update(@PathVariable("id") Long id, @RequestBody TransactionDto dto) {
        try {
            TransactionDto updated = transactionService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (DataValidationException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnknownDataException e) {
            return ResponseEntity.notFound().build();
        }

    }
}