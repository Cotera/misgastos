package com.app.misgastos.model;

import lombok.Data;

@Data
public class AnnotacionDto {

    private Long id;
    private String description;
    private Float amount;

    private AmountTypeEnum type;


}
