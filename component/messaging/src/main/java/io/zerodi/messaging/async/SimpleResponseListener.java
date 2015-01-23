package io.zerodi.messaging.async;

import java.util.concurrent.Future;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.zerodi.messaging.core.UniqueId;
import io.zerodi.messaging.core.UniqueIdFactory;

@Component
public class SimpleResponseListener implements ResponseListener {
    private static final Logger logger = LoggerFactory.getLogger(SimpleResponseListener.class);

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private UniqueIdFactory uniqueIdFactory;

    @Autowired
    private ResponseContainer responseContainer;

    @Override
    public <T> Future<T> awaitResponse(@Nonnull final UniqueId uniqueId) {
        return responseContainer.awaitResponse(uniqueId);
    }

    @Override
    public void notifyWithError(@Nonnull final UniqueId uniqueId, final Throwable error) {
        logger.debug("notifying {} with error {}", uniqueId, error);
        responseContainer.notifyWithError(uniqueId, error);
    }

    @Override
    public void onMessage(@Nonnull final Message message) {
        logger.debug("handling {}", message);

        MessageProperties messageProperties = message.getMessageProperties();
        UniqueId uniqueId = uniqueIdFactory.fromBytes(messageProperties.getCorrelationId());

        try {
            logger.debug("message has correlationId = {}", uniqueId);
            Object responseObject = messageConverter.fromMessage(message);

            responseContainer.notifyWithResponse(uniqueId, responseObject);
        } catch (Exception e) {
            logger.error("while handling message " + message, e);
            throw new RuntimeException(e);
        }
    }
}
