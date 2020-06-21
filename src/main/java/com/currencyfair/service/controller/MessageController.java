package com.currencyfair.service.controller;


import com.currencyfair.service.dao.Message;
import com.currencyfair.service.exception.MessageNotFoundException;
import com.currencyfair.service.exception.MessageServiceException;
import com.currencyfair.service.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    private static final Logger logger= LoggerFactory.getLogger(MessageController.class);

    @GetMapping
    public Iterable findAll() {
        return messageRepository.findAll();
    }

    @GetMapping("/userId/{userId}")
    public Iterable findAllByUserId(@PathVariable int userId) {

        List<Message> result = messageRepository.findByUserId(userId);
        if(result != null) {
            if (!result.isEmpty()) {
                return result;
            } else {
                throw new MessageNotFoundException();
            }
        }else{
            logger.error("Service Problem on findAllByUserId");
            throw new MessageServiceException();
        }
    }

    @GetMapping("/country")
    public Iterable getAllMessagesByCountry() {
        return messageRepository.getAllMessageCountByCountry();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Message msg) {
        try{
            return messageRepository.save(msg);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new MessageServiceException();
        }
    }
}
