package io.pivotal.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    private final String routingKey = "transactions";
    private final String exchangeName = routingKey + "-x";

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Override
    public void run(String... args) throws Exception {
        template.setConfirmCallback((correlationData, ack, s) -> {
            // TODO: handle confirm
            if (!ack) {
                System.out.println("Message not acknowledged by the broker");
            }
        });
        template.setReturnCallback((message, i, s, s1, s2) -> {
            // TODO: handle message returned because unroutable
            System.out.println("message returned!");
        });

        while (true) {
            System.out.println("sending message");

            CorrelationData correlationData = new CorrelationData(String.valueOf(System.currentTimeMillis()));
            template.convertAndSend(
                    exchange.getName(),
                    routingKey,
                    "ciao!",
                    correlationData
            );
            Thread.sleep(200);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
