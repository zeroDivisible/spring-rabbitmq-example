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

    private final Address  responseAddress;
    private final UniqueId correlationId;
    private final UniqueId messageId;

    AddressingPostProcessor(@Nonnull final Address responseAddress, @Nonnull final UniqueId messageId,
                            @Nonnull final UniqueId correlationId) {
        Preconditions.checkNotNull(messageId, "messageId cannot be null!");
        Preconditions.checkNotNull(responseAddress, "responseAddress cannot be null!");
        Preconditions.checkNotNull(correlationId, "correlationId cannot be null!");

        this.responseAddress = responseAddress;
        this.correlationId = correlationId;
        this.messageId = messageId;
    }

    @Override
    public Message postProcessMessage(final Message message) throws AmqpException {
        MessageProperties messageProperties = message.getMessageProperties();

        messageProperties.setReplyToAddress(responseAddress);

        messageProperties.setMessageId(messageId.asString());
        messageProperties.setCorrelationId(correlationId.asByteArray());

        return message;
    }
}
