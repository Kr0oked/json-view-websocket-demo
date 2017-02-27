package com.example;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.core.AbstractMessageSendingTemplate.CONVERSION_HINT_HEADER;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/item")
    public void test() {
        Map<String, Object> headers = new HashMap<>();
        headers.put(CONVERSION_HINT_HEADER, Views.Public.class);
        messagingTemplate.convertAndSend("/destination", new Item(), headers);
    }

    @MessageMapping("/item")
    @JsonView(Views.Public.class)
    @SendToUser(value = "/destination", broadcast = false)
    public Item test2() {
        return new Item();
    }

    public class CustomMapper extends MappingJackson2MessageConverter {

        @Override
        protected Class<?> getSerializationView(Object conversionHint) {
            return Views.Public.class;
        }
    }
}
