package io.pivotal.consumer;

import org.springframework.amqp.rabbit.annotation.*;

@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue(
                                value = "transactions.dead-letters",
                                durable = "true"
                        ),
                        exchange = @Exchange(value = "transactions-x.dead-letters"),
                        key = {"dead"}
                )
        }
)
public class DeadLetterConsumer {
    @RabbitHandler(isDefault = true)
    public void handleMessage(String msg) throws Exception {
        System.out.println("got a message from dead letter queue");
        Thread.sleep(10000);
    }
}
