package io.pivotal.consumer;

import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

	@Bean
	public Consumer consumer() {
		return new Consumer();
	}

	@Bean
	public DeadLetterConsumer deadLetterConsumer() { return new DeadLetterConsumer(); }

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("listening...");
		Thread.sleep(Integer.MAX_VALUE);
	}
}
