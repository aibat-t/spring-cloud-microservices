package com.aibat.bill.controller.dto;

import com.aibat.bill.entity.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
public class BillResponseDTO {

    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDecimal;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;

    public BillResponseDTO(Bill bill){
        billId = bill.getBillId();
        accountId = bill.getAccountId();
        amount = bill.getAmount();
        isDecimal = bill.getIsDecimal();
        creationDate = bill.getCreationDate();
        overdraftEnabled = bill.getOverdraftEnabled();
    }

}
