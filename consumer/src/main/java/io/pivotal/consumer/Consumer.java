package io.pivotal.consumer;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Header;

@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue(value = "hello", durable = "true"),
                        exchange = @Exchange(value = "hello-x", durable = "true"),
                        key = {"foo"}
                )
        }
)
public class Consumer {

    @RabbitHandler(isDefault = true)
    public void receive(String in) {
        System.out.println("received '" + in + "'!");
    }

    @RabbitHandler
    public void redelivered(@Header("redelivered") Object obj) {
        System.out.println("redelivered");
    }
}
