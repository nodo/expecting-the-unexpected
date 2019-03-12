package io.pivotal.rmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RmqApplication implements CommandLineRunner {

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("hello-x");
	}

	@Autowired
	private RabbitTemplate template;

	@Autowired
	DirectExchange exchange;

	public static void main(String[] args) {
		SpringApplication.run(RmqApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			String message = "Ciao!";
			System.out.println("sending '" + message + "'...");
			template.convertAndSend(exchange.getName(), "foo", message);
			Thread.sleep(200);
		}
	}
}
