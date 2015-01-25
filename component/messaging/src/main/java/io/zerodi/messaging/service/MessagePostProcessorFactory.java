package io.zerodi.messaging.service;

import javax.annotation.Nonnull;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.zerodi.messaging.core.UniqueId;

@Component
public class MessagePostProcessorFactory {

    @Qualifier("response-queue-address")
    @Autowired
    protected Address responseQueueAddress;


    public MessagePostProcessor createPostProcessor(@Nonnull final UniqueId messageId, @Nonnull final UniqueId correlationId) {
        return new AddressingPostProcessor(responseQueueAddress, messageId, correlationId);
    }
}
