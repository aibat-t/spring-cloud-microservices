package com.aibat.bill.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;

    public Bill(Long accountId, BigDecimal amount, Boolean isDefault, OffsetDateTime creationDate, Boolean overdraftEnabled) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.creationDate = creationDate;
        this.overdraftEnabled = overdraftEnabled;
    }

    public Bill(Long billId, Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled) {
        this.billId = billId;
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.overdraftEnabled = overdraftEnabled;
    }
}
