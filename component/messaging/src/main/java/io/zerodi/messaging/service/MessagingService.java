package io.zerodi.messaging.service;

import java.util.concurrent.Future;

import org.springframework.amqp.core.Address;

/**
 * Service used to send messages messages, but giving a chance to wait until response comes back.
 */
public interface MessagingService {

    <T> Future<T> send(T payload, Address destination) throws MessagingException;
}
