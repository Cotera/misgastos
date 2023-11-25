package com.app.misgastos.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "ANOTATION")
public class AnnotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column (name = "AMOUNT")
    private Float amount;

    @Column (name = "TYPE")
    private Integer type;

    public AnnotationEntity() {}

    public AnnotationEntity(Long id, String description, Float amount, Integer type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
