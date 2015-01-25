package io.zerodi.messaging.async.response;

import java.util.concurrent.Future;

import javax.annotation.Nonnull;

import org.springframework.amqp.core.MessageListener;

import io.zerodi.messaging.core.UniqueId;

/**
 * Listener, which, when given an {@link io.zerodi.messaging.core.UniqueId}, will create a {@link java.util.concurrent.Future} which can
 * be passed to interested parties, which will be notified when a response comes in for given id.
 */
public interface ResponseListener extends MessageListener {

    <T> Future<T> awaitResponse(@Nonnull UniqueId uniqueId);

    void notifyWithError(@Nonnull UniqueId uniqueId, Throwable error);
}
