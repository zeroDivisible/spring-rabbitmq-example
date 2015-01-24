package io.zerodi.messaging.service;

import javax.annotation.Nonnull;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

import com.google.common.base.Preconditions;
import io.zerodi.messaging.core.UniqueId;

class AddressingPostProcessor implements MessagePostProcessor {

    private final Address  messageDestination;
    private final UniqueId correlationId;
    private final UniqueId messageId;

    AddressingPostProcessor(@Nonnull final Address destination, @Nonnull final UniqueId messageId,
                            @Nonnull final UniqueId correlationId) {
        Preconditions.checkNotNull(messageId, "messageId cannot be null!");
        Preconditions.checkNotNull(destination, "destination cannot be null!");
        Preconditions.checkNotNull(correlationId, "correlationId cannot be null!");

        this.messageDestination = destination;
        this.correlationId = correlationId;
        this.messageId = messageId;
    }

    @Override
    public Message postProcessMessage(final Message message) throws AmqpException {
        MessageProperties messageProperties = message.getMessageProperties();

        messageProperties.setReplyToAddress(messageDestination);

        byte[] correlationIdAsByteArray = correlationId.asByteArray();
        messageProperties.setCorrelationId(correlationIdAsByteArray);

        messageProperties.setMessageId(messageId.asString());

        return message;
    }
}
