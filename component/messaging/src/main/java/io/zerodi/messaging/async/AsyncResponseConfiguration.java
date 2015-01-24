package io.zerodi.messaging.async;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.zerodi.messaging.configuration.MessagingConfiguration;
import io.zerodi.messaging.core.UniqueId;
import io.zerodi.messaging.core.UniqueIdFactory;

@Configuration
public class AsyncResponseConfiguration {

    @Autowired
    private MessagingConfiguration messagingConfiguration;

    @Autowired
    private UniqueIdFactory uniqueIdFactory;

    @Qualifier("response-queue")
    @Bean
    protected Queue responseQueue() {
        return new Queue(formatResponseQueueName(), false, false, true);
    }

    @Qualifier("response-exchange")
    @Bean
    protected Exchange responseExchange() {
        return new TopicExchange(messagingConfiguration.getResponseExchangeName(), true, false);
    }

    /**
     * This bean is mandatory, even though it's not used anywhere explicitly. It binds the queue which we are passing with the
     * exchange on which we want to receive responses.
     */
    @Qualifier("response-queue-binding")
    @Bean
    protected Binding responseQueueBinding() {
        return BindingBuilder.bind(responseQueue()).to(responseExchange()).with(responseQueue().getName()).noargs();
    }

    private String formatResponseQueueName() {
        UniqueId uniqueId = uniqueIdFactory.createUniqueId();
        return String.format("%s.%s", messagingConfiguration.getResponseQueuePrefix(), uniqueId);
    }
}
