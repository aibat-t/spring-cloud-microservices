package com.aibat.account.controller.dto;

import com.aibat.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountResponseDTO {

    private long accountId;

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime creationDate;

    private List<Long> bills;

    public AccountResponseDTO(Account account){
        accountId = account.getAccountId();
        name = account.getName();
        email = account.getEmail();
        phone = account.getPhone();
        creationDate = account.getCreationDate();
        bills = account.getBills();
    }
}
