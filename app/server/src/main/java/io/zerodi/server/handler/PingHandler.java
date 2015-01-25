package io.zerodi.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import io.zerodi.messaging.amqp.MessageHandler;
import io.zerodi.ping.PingMessage;
import io.zerodi.server.Clock;

@Component
public class PingHandler implements MessageHandler<PingMessage, PingMessage> {

    @Autowired
    private Clock clock;

    @Override
    public PingMessage handleMessage(final PingMessage message) {
        Preconditions.checkNotNull(message, "message cannot be null!");
        return new PingMessage(createResponse(message), clock.getCurrentDate());
    }

    private String createResponse(final PingMessage message) {
        return "response to: " + message.getPayload();
    }
}
