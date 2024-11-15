package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messsagRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messsagRepository){
        this.messsagRepository = messsagRepository;
    }
    

    public Message createMessage(Message message){
        int account_id = message.getPostedBy();
        Optional<Account> optionalAccount = accountRepository.findById(account_id);
        if(message.getMessageText() != "" && message.getMessageText().length() <= 255
         && optionalAccount.isPresent()){
            return messsagRepository.save(message);
         } else{
            return null;
         }
    }

    public List<Message> getAllMessages(){
        return messsagRepository.findAll();
    }

    public Message getMessageById(int id){
        Optional<Message> optionalMessage = messsagRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        } else{
            return null;
        }
    }

    public void deleteMesage(int id){
        Optional<Message> optionalMessage = messsagRepository.findById(id);
        if(optionalMessage.isPresent()){
            messsagRepository.deleteById(id);
        }
        
    }

    public void updateMessage(int id, Message updatedText){
        if(updatedText.getMessageText() == "" || updatedText.getMessageText().length() > 255){}else{
            Optional<Message> optionalMessage = messsagRepository.findById(id);
            if(optionalMessage.isPresent()){
                Message message = optionalMessage.get();
                message.setMessageText(updatedText.getMessageText());
                messsagRepository.save(message);
            }
        }
    }

    public List<Message> retrieveMessagesFromUser(int id){
        return messsagRepository.retrieveMessageByPostedByID(id);
    }

}
