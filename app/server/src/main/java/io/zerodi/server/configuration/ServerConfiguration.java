package io.zerodi.server.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.zerodi.messaging.amqp.SimpleMessageListenerContainerFactory;
import io.zerodi.messaging.amqp.MessageHandlerFactory;
import io.zerodi.server.handler.PingHandler;

@Configuration
@ComponentScan(value = "io.zerodi.server")
public class ServerConfiguration {
    @Autowired
    private SimpleMessageListenerContainerFactory messageListenerContainerFactory;

    @Autowired
    private MessageHandlerFactory messageHandlerFactory;

    @Autowired
    private PingHandler pingHandler;

    @Qualifier("amqp-ping-queue")
    @Autowired
    private Queue pingQueue;

    @Bean
    public SimpleMessageListenerContainer pingListenerContainer() {
        return messageListenerContainerFactory.createMessageListenerContainer(createPingHandler(), pingQueue);
    }

    private MessageListenerAdapter createPingHandler() {
        return messageHandlerFactory.newMessageListenerAdapter(pingHandler);
    }
}
