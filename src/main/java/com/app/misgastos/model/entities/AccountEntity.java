package com.app.misgastos.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
@Data
public class AccountEntity { //reppasar esto mañana contra la base de datos 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @Column (name = "NAME")
    private String name;

    @Column (name = "CURRENCY")
    private String currency;

    @Column (name = "INITIAL_BALANCE")
    private Double initialBalance;
}
