package com.app.misgastos.model;

public enum TransactionTypeEnum {
    EXPEND(1, "Expent"),
    INCOME (2, "Income"),
    TRANSFER (3, "Transfer");


    private int id;
    private String description;

    TransactionTypeEnum(int id, String description) {
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

    public static TransactionTypeEnum getFromId(Integer v){
        if (v == null) {
            return null;
        }
        switch (v) {
            case 1:
                return TransactionTypeEnum.EXPEND;
            case 2:
                return TransactionTypeEnum.INCOME;
            case 3:
                return TransactionTypeEnum.TRANSFER;
            default:
                return null;
        }

    }
}
