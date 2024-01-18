package com.app.misgastos.web;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.services.AccountService;
import com.app.misgastos.services.impl.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto){
        AccountDto createdAccount = accountService.createAccount(accountDto).orElse(null);
        if (Objects.nonNull(createdAccount)){
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdAccount.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdAccount);
        } else {
            return ResponseEntity.badRequest().body(accountDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable("id") Long id){
        AccountDto foundAccount = accountService.getById(id);
        if (foundAccount!=null) {
            return ResponseEntity.ok(foundAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<AccountDto> getAll(){
        accountService.getAll();
        return null;
    }

    @DeleteMapping
    public ResponseEntity<AccountDto> deleteById(@PathVariable("id") Long id){
        try {
            accountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<AccountDto> update(@PathVariable("id") Long id, @RequestBody AccountDto accountDto){
        try {
            AccountDto updatedAccount = accountService.update(id, accountDto);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
