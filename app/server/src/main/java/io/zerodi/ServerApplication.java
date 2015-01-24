package io.zerodi;

import java.util.Date;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.zerodi.messaging.amqp.SimpleMessageListenerContainerFactory;
import io.zerodi.messaging.async.MessageHandler;
import io.zerodi.messaging.async.MessageHandlerFactory;
import io.zerodi.ping.PingMessage;
import io.zerodi.ping.configuration.PingConfiguration;

@Configuration
@EnableAutoConfiguration
@Import({PingConfiguration.class})
public class ServerApplication extends SpringBootServletInitializer {

    @Autowired
    private SimpleMessageListenerContainerFactory messageListenerContainerFactory;

    @Autowired
    private MessageHandlerFactory messageHandlerFactory;

    @Qualifier("amqp-ping-queue")
    @Autowired
    private Queue pingQueue;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }


    @Bean
    public SimpleMessageListenerContainer pingListenerContainer() {
        return messageListenerContainerFactory.createMessageListenerContainer(createPingHandler(), pingQueue);
    }

    private MessageListenerAdapter createPingHandler() {
        return messageHandlerFactory.newMessageListenerAdapter(new MessageHandler<PingMessage, PingMessage>() {
            @Override public PingMessage handleMessage(final PingMessage message) {
                return new PingMessage("dupa", new Date());
            }
        });
    }
}
