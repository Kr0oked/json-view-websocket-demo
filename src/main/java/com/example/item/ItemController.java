package com.example.item;

import java.security.Principal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import static org.springframework.messaging.core.AbstractMessageSendingTemplate.CONVERSION_HINT_HEADER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    @NonNull
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messaging-template")
    public void useMessagingTemplate(Principal principal) {
        log.info("Sending message with messagingTemplate");

        var user = principal.getName();
        var destination = "/response";
        var item = getItem();
        var headers = getHeaders();

        messagingTemplate.convertAndSendToUser(user, destination, item, headers);
    }

    private Map<String, Object> getHeaders() {
        return Map.of(CONVERSION_HINT_HEADER, Views.Public.class);
    }

    @MessageMapping("/send-to-user-annotation")
    @SendToUser(value = "/response", broadcast = false)
    @JsonView(Views.Public.class)
    public Item useSendToUserAnnotation() {
        log.info("Sending message with SendToUser annotation");
        return getItem();
    }

    private Item getItem() {
        return new Item()
                .setId(1)
                .setItemName("the-item-name")
                .setOwnerName("the-owner-name");
    }
}
