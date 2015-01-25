package io.zerodi.messaging.amqp;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory which can be used to obtain preconfigured instances of {@link org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter}
 * <p/>
 * <p>(messages being sent are serialized to JSON, using a {@link org.springframework.amqp.support.converter.MessageConverter},
 * this factory makes sure that the same converter will be used in our handlers.)</p>
 */
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
