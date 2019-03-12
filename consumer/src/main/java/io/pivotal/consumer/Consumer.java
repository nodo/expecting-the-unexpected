package io.pivotal.consumer;

import org.springframework.amqp.rabbit.annotation.*;

@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue("hello"),
                        exchange = @Exchange("hello-x"),
                        key = {"foo"}
                )
        }
)
public class Consumer {

    @RabbitHandler
    public void receive(String in) {
        System.out.println("received '" + in + "'!");
    }
}
