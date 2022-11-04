package com.aibat.deposit.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @GetMapping(value = "bills/{billId}")
    BillResponseDTO getBillById(@PathVariable("billId") Long billId);

    @PutMapping(value = "bills/{billId}")
    void update(@PathVariable("billId") Long billId, BillRequestDTO billRequestDTO);

    @GetMapping(value = "bills/account/{accountId}")
    List<BillResponseDTO> getBillsByAccountId(@PathVariable("accountId") Long accountId);
}
