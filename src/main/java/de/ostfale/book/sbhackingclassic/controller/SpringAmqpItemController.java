package de.ostfale.book.sbhackingclassic.controller;

import de.ostfale.book.sbhackingclassic.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class SpringAmqpItemController {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpItemController.class);

    // autoconfiguration for amqpTemplate -> RabbitMQTemplate
    private final AmqpTemplate amqpTemplate;

    public SpringAmqpItemController(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping("/items")
    ResponseEntity<?> addNewItemUsingSpringAmqp(@RequestBody Item item) {
        this.amqpTemplate.convertAndSend("hacking-spring-boot", "new-items-spring-amqp", item);
        // return HTTP 201 created with relative URI back to this endpoint
        return ResponseEntity.created(URI.create("/items")).build();
    }
}
