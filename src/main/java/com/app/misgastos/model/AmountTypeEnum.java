package com.app.misgastos.model;

public enum AmountTypeEnum {
    EXPEND(1, "Expent"),
    INCOME (2, "Income"),
    TRANSFER (3, "Transfer");


    private int id;
    private String description;

    AmountTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static AmountTypeEnum getFromId(int v){
        switch (v) {
            case 1:
                return AmountTypeEnum.EXPEND;
            case 2:
                return AmountTypeEnum.INCOME;
            case 3:
                return AmountTypeEnum.TRANSFER;
            default:
                return null;
        }

    }
}
