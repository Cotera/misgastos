package com.app.misgastos.utils.converters;

import com.app.misgastos.model.TransactionDto;
import com.app.misgastos.model.TransactionTypeEnum;
import com.app.misgastos.model.entities.TransactionEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class TransactionConverter {

    public static TransactionDto toDto(TransactionEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        TransactionDto dto = new TransactionDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setType(TransactionTypeEnum.getFromId(entity.getType()));       

        return dto;
    }

    public static TransactionEntity toEntity(TransactionDto dto) {
        if (isNull(dto)) {
            return null;
        }
        TransactionEntity entity = new TransactionEntity();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        if (nonNull(dto.getType())) {
            entity.setType(dto.getType().getId());
        }
        if (nonNull(dto.getAccountDto())) {
            entity.setAccount(dto.getAccountDto().getId());
        }

        return entity;
    }

    public static List<TransactionDto> toDtos(List<TransactionEntity> entities) {
        if (isNull(entities) || entities.isEmpty()) {
            return new ArrayList<>();
        }
        List<TransactionDto> dtos = new ArrayList<>();

        for (TransactionEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }

    public static List<TransactionEntity> toEntities(List<TransactionDto> dtos) {
        if (isNull(dtos) || dtos.isEmpty()) {
            return new ArrayList<>();
        }
        List<TransactionEntity> entities = new ArrayList<>();

        for (TransactionDto dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
