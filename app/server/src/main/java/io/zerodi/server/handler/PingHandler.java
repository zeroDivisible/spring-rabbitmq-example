package io.zerodi.server.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import io.zerodi.environment.service.Clock;
import io.zerodi.messaging.amqp.MessageHandler;
import io.zerodi.ping.PingMessage;

/**
 * Handles incoming ping messages, responding with pong.
 */
@Component
public class PingHandler implements MessageHandler<PingMessage, PingMessage> {
    private static final Logger logger = LoggerFactory.getLogger(PingHandler.class);

    @Autowired
    private Clock clock;

    @Override
    public PingMessage handleMessage(final PingMessage message) {
        Preconditions.checkNotNull(message, "message cannot be null!");
        logger.info("handling ping message: {}", message);

        String response = createResponse(message);
        Date currentDate = clock.getCurrentDate();

        return new PingMessage(response, currentDate);
    }

    private String createResponse(final PingMessage message) {
        return "response to: " + message.getPayload();
    }
}
