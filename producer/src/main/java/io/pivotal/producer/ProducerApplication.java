package io.pivotal.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {


    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("hello-x", true, false);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    private String routingKey = "foo";

    @Override
    public void run(String... args) throws Exception {
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // TODO:
        });
        while (true) {
            System.out.println("sending message");

            template.invoke(t -> {
                String message = "Ciao!";
                CorrelationData correlationData = new CorrelationData(String.valueOf(System.currentTimeMillis()));
                t.convertAndSend(exchange.getName(), routingKey, message, correlationData);
                boolean ack = t.waitForConfirms(500);
                if (!ack) {
                    System.out.printf("Timed out");
                    return false;
                }
                return true;
            });
            Thread.sleep(200);
        }
    }
}
