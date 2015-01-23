package io.zerodi.messaging.service;

import javax.annotation.Nonnull;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import io.zerodi.messaging.core.UniqueId;

@Component
public class MessagePostProcessorFactory {

    public MessagePostProcessor createPostProcessor(@Nonnull final Address destination, @Nonnull final UniqueId messageId,
                                                    @Nonnull final UniqueId correlationId) {
        return new AddressingPostProcessor(destination, messageId, correlationId);
    }
}
