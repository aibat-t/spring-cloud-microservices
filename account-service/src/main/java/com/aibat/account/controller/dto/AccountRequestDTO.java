package com.aibat.account.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountRequestDTO {

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime creationDate;

    private List<Long> bills;
}
