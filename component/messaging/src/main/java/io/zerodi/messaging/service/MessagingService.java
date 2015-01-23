package io.zerodi.messaging.service;

import java.util.concurrent.Future;

import org.springframework.amqp.core.Address;

public interface MessagingService {

    <T> Future<T> send(T payload, Address destination) throws MessagingException;
}
