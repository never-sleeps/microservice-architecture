package com.microservicearchitecture.messaging;

import com.microservicearchitecture.messaging.handlers.OrderCreatedHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Slf4j
@Component
public class OrderNotificationListener {

    private final OrderCreatedHandler orderCreatedHandler;

    @Autowired
    public OrderNotificationListener(OrderCreatedHandler orderCreatedHandler) {
        this.orderCreatedHandler = orderCreatedHandler;
    }

    @KafkaListener(
            topics = "order_notification",
            groupId = "order_group",
            clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderCreatedEvent event) {
        orderCreatedHandler.process(event);
        log.info("Сообщение успешно обработано");
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
