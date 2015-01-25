package io.zerodi.messaging.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import io.zerodi.messaging.async.response.ResponseListener;
import io.zerodi.messaging.core.UniqueId;
import io.zerodi.messaging.core.UniqueIdFactory;

@Service class AmqpMessagingService implements MessagingService {
    private static final Logger logger = LoggerFactory.getLogger(AmqpMessagingService.class);

    @Autowired
    private UniqueIdFactory uniqueIdFactory;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessagePostProcessorFactory postProcessorFactory;

    @Autowired
    private ResponseListener responseListener;

    @Override
    public <T> Future<T> send(final T payload, final Address destination) throws MessagingException {
        Preconditions.checkNotNull(payload, "payload cannot be null!");
        Preconditions.checkNotNull(destination, "destination cannot be null!");

        try {
            UniqueId messageId = uniqueIdFactory.createUniqueId();
            UniqueId correlationId = uniqueIdFactory.createUniqueId();

            Future<T> responseFuture = responseListener.awaitResponse(correlationId);
            MessagePostProcessor postProcessor = postProcessorFactory.createPostProcessor(messageId, correlationId);

            amqpTemplate.convertAndSend(destination.getExchangeName(), destination.getRoutingKey(), payload, postProcessor);
            return responseFuture;
        } catch (Exception e) {
            logger.warn("exception when sending message", e);
            throw new MessagingException(e);
        }
    }
}
