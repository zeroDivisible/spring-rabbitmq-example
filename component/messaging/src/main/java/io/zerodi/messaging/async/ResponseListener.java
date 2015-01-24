package io.zerodi.messaging.async;

import java.util.concurrent.Future;

import javax.annotation.Nonnull;

import org.springframework.amqp.core.MessageListener;

import io.zerodi.messaging.core.UniqueId;

public interface ResponseListener extends MessageListener {

    <T> Future<T> awaitResponse(@Nonnull UniqueId uniqueId);

    void notifyWithError(@Nonnull UniqueId uniqueId, Throwable error);
}
