package io.pivotal.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import java.util.Random;

@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue(
                                value = "transactions",
                                durable = "true",
                                autoDelete = "false",
                                arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "transactions-x.dead-letters"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dead")
                            }
                        ),
                        exchange = @Exchange(value = "transactions-x"),
                        key = {"transactions"}
                )
        }
)
public class Consumer {
    @RabbitHandler(isDefault = true)
    public void handleMessage(@Payload String content, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) throws AmqpRejectAndDontRequeueException {
        if (redelivered) {
            // TODO: handle possible duplicates
        }
        if (new Random().nextBoolean()) {
            throw new AmqpRejectAndDontRequeueException("failure");
        } else {
            System.out.println("cool.");
        }
    }
}
