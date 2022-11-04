package com.aibat.bill.controller;

import com.aibat.bill.controller.dto.BillRequestDTO;
import com.aibat.bill.controller.dto.BillResponseDTO;
import com.aibat.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping("/{billId}")
    public BillResponseDTO getBill(@PathVariable Long billId){
        return new BillResponseDTO(billService.getBillById(billId));
    }

    @PostMapping("/")
    public Long createBill(@RequestBody BillRequestDTO billRequestDTO){
        return billService.createBill(billRequestDTO.getAccountId(), billRequestDTO.getAmount(), billRequestDTO.getIsDefault(), billRequestDTO.getOverdraftEnabled());
    }

    @PutMapping("/{billId}")
    public BillResponseDTO updateBill(@PathVariable Long billId,
                                      @RequestBody BillRequestDTO billRequestDTO){
        return new BillResponseDTO(billService.updateBill(billId,billRequestDTO.getAccountId(), billRequestDTO.getAmount(), billRequestDTO.getIsDefault(), billRequestDTO.getOverdraftEnabled()));
    }

    @DeleteMapping("/{billId}")
    public BillResponseDTO deleteBill(@PathVariable Long billId){
        return new BillResponseDTO(billService.deleteBill(billId));
    }

    @GetMapping(value = "/account/{accountId}")
    public List<BillResponseDTO> getBillsByAccountId(@PathVariable("accountId") Long accountId){
        return billService.getBillsByAccountId(accountId)
                .stream()
                .map(BillResponseDTO::new)
                .collect(Collectors.toList());
    }
}
