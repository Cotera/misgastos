package com.app.misgastos.utils.converters;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.entities.AccountEntity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class AccountConverter {

    public static AccountDto toDto(AccountEntity entity) {
        if (Objects.isNull(entity)){
            return null;
        }
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCurrency(Currency.getInstance(entity.getCurrency()));
        dto.setTransactions(null);
        return dto;
    }

    public static AccountEntity toEntity(AccountDto dto) {
        if (Objects.isNull(dto)){
            return null;
        }
        AccountEntity entity = new AccountEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCurrency(dto.getCurrency().getCurrencyCode());
        return entity;
    }

    public static List<AccountDto> toDtos(List<AccountEntity> entities) {
        List<AccountDto> dtos = new ArrayList<>();
        for (AccountEntity entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }

    public static List<AccountEntity> toEntities(List<AccountDto> dtos) {
        List<AccountEntity> entities = new ArrayList<>();
        for (AccountDto dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
