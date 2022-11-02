package com.aibat.deposit.controller;

import com.aibat.deposit.controller.dto.DepositResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    @PostMapping("/deposits")
    public DepositResponseDTO deposit(){
        return new DepositResponseDTO();
    }
}
