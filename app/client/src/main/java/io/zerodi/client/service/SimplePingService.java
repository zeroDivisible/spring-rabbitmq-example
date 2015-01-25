package io.zerodi.client.service;

import java.util.Date;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.zerodi.messaging.service.MessagingService;
import io.zerodi.ping.PingMessage;

@Service
class SimplePingService implements PingService {
    private static final Logger logger = LoggerFactory.getLogger(SimplePingService.class);

    private static final String DEFAULT_MESSAGE = "ping";

    @Autowired
    private MessagingService messagingService;

    @Qualifier("amqp-ping-address")
    @Autowired
    private Address pingAddress;

    @Override
    public Future<PingMessage> sendPing(final String message) {
        String messageToSend = DEFAULT_MESSAGE;
        if (StringUtils.hasText(message)) {
            messageToSend = message;
        }

        PingMessage pingMessage = new PingMessage(messageToSend, new Date());
        logger.debug("will send {}", pingMessage);
        return messagingService.send(pingMessage, pingAddress);
    }
}
