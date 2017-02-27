package com.example;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.core.AbstractMessageSendingTemplate.CONVERSION_HINT_HEADER;

@Controller
public class ItemController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ItemController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/messaging-template")
    public void getFrommessagingTemplate(Principal principal) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(CONVERSION_HINT_HEADER, Views.Public.class);
        messagingTemplate.convertAndSendToUser(principal.getName(), "/messaging-template-reply", getItem(), headers);
    }

    @MessageMapping("/send-to-user")
    @SendToUser(value = "/send-to-user-reply", broadcast = false)
    @JsonView(Views.Public.class)
    public Item getFromSendToUser() {
        return getItem();
    }

    private Item getItem() {
        Item item = new Item();
        item.setId(1);
        item.setItemName("the-item-name");
        item.setOwnerName("the-owner-name");
        return item;
    }
}
