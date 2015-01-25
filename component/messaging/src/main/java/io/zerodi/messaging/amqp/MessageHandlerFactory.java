package io.zerodi.messaging.amqp;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerFactory {

    @Autowired
    private MessageConverter messageConverter;

    public <Request, Response> MessageListenerAdapter newMessageListenerAdapter(MessageHandler<Request, Response> messageHandler) {
        return new MessageListenerAdapter(messageHandler, messageConverter);
    }

    public MessageListenerAdapter newMessageListenerAdapter(MessageListener messageListener) {
        return new MessageListenerAdapter(messageListener, messageConverter);
    }

}
