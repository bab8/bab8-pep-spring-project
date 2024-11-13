package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account){
        if(!userExists(account) && account.getPassword().length() > 3){
            return accountRepository.save(account);
        } else{
            return null;
        }
    }

    public Account login(Account account){
        return accountRepository.login(account.getUsername(), account.getPassword());
    }
    
    public Boolean userExists(Account account){
        if(accountRepository.userExists(account.getUsername()) != null){
            return true;
        } else{
            return false;
        }
    }
}
