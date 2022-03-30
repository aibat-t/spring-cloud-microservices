package com.aibat.bill.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class BillRequestDTO {

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDecimal;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;
}
