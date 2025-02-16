package com.airplane.schedule.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_QUEUE = "order-queue";
    public static final String DELAYED_QUEUE = "delayed-order-queue";
    public static final String EXCHANGE = "order-exchange";
    public static final String DEAD_LETTER_EXCHANGE = "order-dead-letter-exchange";
    public static final String DEAD_LETTER_QUEUE = "order-dead-letter-queue";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "dead")
                .withArgument("x-message-ttl", 180000)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding delayedQueueBinding() {
        return BindingBuilder.bind(delayedQueue()).to(orderExchange()).with("delayed");
    }

    @Bean
    public Binding deadLetterQueueBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("dead");
    }
}
