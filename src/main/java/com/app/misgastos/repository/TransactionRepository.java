package com.app.misgastos.repository;

import com.app.misgastos.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    /**
     * Busca todas las transacciones que corresponden a una cuenta dada
     *
     * @param id id del Account
     * @return Lista de TransactionEntity
     *//*
    @Query("SELECT * FROM TRANSACTION " +
            "WHERE ACCOUNT = {}")
    List<TransactionEntity> getByAccountId(Long id);*/
}
