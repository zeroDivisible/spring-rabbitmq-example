package io.zerodi.messaging.async;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class MessageHandlerFactory {

    @Autowired
    private MessageConverter messageConverter;

    public <Request, Response> MessageListenerAdapter newMessageListenerAdapter(MessageHandler<Request, Response> messageHandler) {
        return new MessageListenerAdapter(messageHandler, messageConverter);
    }
}
