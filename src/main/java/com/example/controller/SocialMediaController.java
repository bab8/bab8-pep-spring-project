package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
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
    @Autowired
    MessageService messageService;

    @PostMapping(value = "/register")
    public ResponseEntity postRegister(@RequestBody Account account){
        if(accountService.userExists(account)){
            return ResponseEntity.status(409).build();
        }
        Account newUser = accountService.addAccount(account);
        if(newUser != null){
            return ResponseEntity.status(200).body(newUser);
        } else{
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity postLogin(@RequestBody Account account){
        Account loggedIn = accountService.login(account);
        if(loggedIn != null){
            return ResponseEntity.status(200).body(loggedIn);
        } else{
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            return ResponseEntity.status(200).body(createdMessage);
        }else{
            return ResponseEntity.status(400).build();
        }
        
    }

    @GetMapping(value = "/messages")
    public ResponseEntity retrieveAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity retrieveMessageById(@PathVariable int messageId){
        Message message = messageService.getMessageById(messageId);
        if(message == null){
            return ResponseEntity.status(200).build();
        }else{
            return ResponseEntity.status(200).body(message);
        }
    }

    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable int messageId){
        Message message = messageService.getMessageById(messageId);
        if(message == null){
            return ResponseEntity.status(200).build();
        }
        messageService.deleteMesage(messageId);
        message = messageService.getMessageById(messageId);
        if(message == null){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity updateMessageById(@PathVariable int messageId, @RequestBody Message message){
        messageService.updateMessage(messageId, message);
        Message updatedMessage = messageService.getMessageById(messageId);
        if(updatedMessage == null){
            return ResponseEntity.status(400).build();
        }
        if(updatedMessage.getMessageText() == message.getMessageText()){
            return ResponseEntity.status(200).body(1);
        }else{
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity retrieveMessagesByAccountId(@PathVariable int accountId){
        List<Message> messages = messageService.retrieveMessagesFromUser(accountId);
        return ResponseEntity.status(200).body(messages);
    }



}
