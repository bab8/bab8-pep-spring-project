package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    MessageService messageService;

    @PostMapping(value = "/register")
    public ResponseEntity postRegister(@RequestBody Account account){
        if(accountService.userExists(account)){
            return ResponseEntity.status(409).body("Duplicate Username");
        }
        Account newUser = accountService.addAccount(account);
        if(newUser != null){
            return ResponseEntity.status(200).body(newUser);
        } else{
            return ResponseEntity.status(400).body("Error");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity postLogin(@RequestBody Account account){
        Account loggedIn = accountService.login(account);
        if(loggedIn != null){
            return ResponseEntity.status(200).body(loggedIn);
        } else{
            return ResponseEntity.status(401).body("Invalid Login");
        }
    }

}
