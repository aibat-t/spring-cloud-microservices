package com.aibat.account.controller;

import com.aibat.account.controller.dto.AccountRequestDTO;
import com.aibat.account.controller.dto.AccountResponseDTO;
import com.aibat.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountResponseDTO getAccount(@PathVariable Long accountId){
        return new AccountResponseDTO(accountService.getAccountById(accountId));
    }

    @PostMapping("/")
    public Long createAccount(@RequestBody AccountRequestDTO accountRequestDTO){
        return accountService.createAccount( accountRequestDTO.getName(), accountRequestDTO.getEmail(), accountRequestDTO.getPhone(), accountRequestDTO.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDTO updateAccount(@PathVariable Long accountId,
                                            @RequestBody AccountRequestDTO accountRequestDTO){
        return new AccountResponseDTO(accountService.updateAccount(accountId,accountRequestDTO.getName(), accountRequestDTO.getEmail(), accountRequestDTO.getPhone(), accountRequestDTO.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDTO deleteAccount(@PathVariable Long accountId){
        return new AccountResponseDTO(accountService.delateAccount(accountId));
    }
}
