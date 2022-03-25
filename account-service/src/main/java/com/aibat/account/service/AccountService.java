package com.aibat.account.service;

import com.aibat.account.entity.Account;
import com.aibat.account.exception.AccountNotFoundException;
import com.aibat.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getAccountById(Long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Unable to find account with id: " + accountId));
    }

    public Long createAccount(String name, String email, String phone, List<Long> bills){
        Account account = new Account(name, email, phone, OffsetDateTime.now(),bills);
        return accountRepository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, String name, String email, String phone, List<Long> bills) {

        Account account =  new Account();
        account.setAccountId(accountId);
        account.setBills(bills);
        account.setEmail(email);
        account.setName(name);
        account.setPhone(phone);

        return accountRepository.save(account);
    }

    public Account delateAccount(Long accountId){
        Account deleteAccount = getAccountById(accountId);
        accountRepository.deleteById(accountId);
        return deleteAccount;
    }
}
