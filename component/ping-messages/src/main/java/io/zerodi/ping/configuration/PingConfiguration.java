package io.zerodi.ping.configuration;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.zerodi.messaging.configuration.MessagingConfiguration;

@Import({MessagingConfiguration.class})
@Configuration
public class PingConfiguration {

    private static final String PING_ROUTING_KEY = "ping";

    @Qualifier("amqp-ping-queue")
    @Bean
    public Queue pongQueue() {
        return new Queue("pong-queue", true, false, false);
    }

    @Bean
    public Exchange pongExchange() {
        return new TopicExchange("pong-exchange", true, false);
    }

    @Bean
    public Binding pongQueueBinding() {
        return BindingBuilder.bind(pongQueue()).to(pongExchange()).with(PING_ROUTING_KEY).noargs();
    }

    @Qualifier("amqp-ping-address")
    @Bean
    public Address pongQueueAddress() {
        return new Address(pongExchange().getName(), PING_ROUTING_KEY);
    }
}
