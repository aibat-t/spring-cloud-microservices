package com.aibat.deposit.service;

import com.aibat.deposit.entity.Deposit;
import com.aibat.deposit.exception.DepositNotFoundException;
import com.aibat.deposit.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;

    public Deposit getDepositById(Long depositId){
        return depositRepository.findById(depositId)
                .orElseThrow(() -> new DepositNotFoundException("Unable to find deposit wiht id:" + depositId));
    }

    public Long createDeposit(BigDecimal amount, Long billId, String email){
        Deposit deposit = new Deposit(amount, billId, OffsetDateTime.now(), email);
        return depositRepository.save(deposit).getDepositId();
    }

    public Deposit updateDeposit(Long depositId, BigDecimal amount, Long billId, String email){
        Deposit deposit = new Deposit(amount, billId, OffsetDateTime.now(), email);
        deposit.setDepositId(depositId);
        return depositRepository.save(deposit);
    }

    public Deposit deleteDeposit(Long depositId){
        Deposit deletedDeposit = getDepositById(depositId);
        depositRepository.deleteById(depositId);
        return deletedDeposit;
    }
}
