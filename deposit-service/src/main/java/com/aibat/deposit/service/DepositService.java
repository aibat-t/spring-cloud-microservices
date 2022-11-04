package com.aibat.deposit.service;

import com.aibat.deposit.controller.dto.DepositResponseDTO;
import com.aibat.deposit.entity.Deposit;
import com.aibat.deposit.exception.DepositNotFoundException;
import com.aibat.deposit.exception.DepositServiceException;
import com.aibat.deposit.repository.DepositRepository;
import com.aibat.deposit.rest.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;

    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

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

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount){
        if(accountId == null && billId == null){
            throw new DepositServiceException("Account is null bill is null");
        }

        if(billId != null){
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = createBillRequest(amount, billResponseDTO);

            billServiceClient.update(billId, billRequestDTO);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDTO.getAccountId());

            depositRepository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponseDTO.getEmail()));

            return createResponseDTO(amount, accountResponseDTO);
        }

        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequestDTO = createBillRequest(amount, defaultBill);

        billServiceClient.update(defaultBill.getBillId(), billRequestDTO);
        AccountResponseDTO account = accountServiceClient.getAccountById(accountId);
        depositRepository.save( new Deposit(amount, defaultBill.getBillId(), OffsetDateTime.now(), account.getEmail()));

        return createResponseDTO(amount, account);
    }

    private DepositResponseDTO createResponseDTO(BigDecimal amount, AccountResponseDTO accountResponseDTO) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(amount, accountResponseDTO.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDTO));
        } catch (JsonProcessingException e) {
            throw new DepositServiceException("Can't send message to RabbitMQ");
        }

        return depositResponseDTO;
    }

    private static BillRequestDTO createBillRequest(BigDecimal amount, BillResponseDTO billResponseDTO) {
        BillRequestDTO billRequestDTO = new BillRequestDTO();

        billRequestDTO.setAccountId(billResponseDTO.getAccountId());
        billRequestDTO.setCreationDate(billResponseDTO.getCreationDate());
        billRequestDTO.setIsDefault(billResponseDTO.getIsDefault());
        billRequestDTO.setOverdraftEnabled(billResponseDTO.getOverdraftEnabled());
        billRequestDTO.setAmount(billResponseDTO.getAmount().add(amount));
        return billRequestDTO;
    }

    public BillResponseDTO getDefaultBill(Long accountId){
        return billServiceClient.getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDTO::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find default bill for account:" + accountId));
    }
}
