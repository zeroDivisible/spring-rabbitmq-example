package io.zerodi.messaging.async.response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.SettableFuture;
import io.zerodi.messaging.core.UniqueId;

/**
 * Very simple container for storing "callbacks" interested in incoming responses
 */
@Component
public class ResponseContainer {
    private static final Logger logger = LoggerFactory.getLogger(ResponseContainer.class);

    protected final Map<UniqueId, SettableFuture<?>> pendingResponses = new ConcurrentHashMap<>();

    @Nonnull <T> Future<T> awaitResponse(@Nonnull UniqueId uniqueId) {
        SettableFuture<T> future = SettableFuture.create();
        pendingResponses.put(uniqueId, future);

        return future;
    }

    /**
     * Notifies future (identified by id <code>uniqueId</code>, which is awaiting a response, with incoming response
     */
    @SuppressWarnings("unchecked")
    <T> void notifyWithResponse(@Nonnull UniqueId uniqueId, @Nonnull T response) {
        SettableFuture<?> future = pendingResponses.remove(uniqueId);

        if (future == null) {
            logger.warn("no pending request for [ {} ]!", uniqueId);
            return;
        }

        logger.debug("notifying [ {} ] with [ {} ]", uniqueId, response);

        // TODO I couldn't come with a better way of doing this given the time constraints.
        ((SettableFuture<Object>) future).set(response);
    }

    void notifyWithError(@Nonnull UniqueId uniqueId, @Nonnull Throwable error) {
        SettableFuture<?> settableFuture = pendingResponses.remove(uniqueId);

        if (settableFuture == null) {
            logger.warn("no pending request for [ {} ]!", uniqueId);
            return;
        }

        logger.debug("notifying [ {} ] with error [ {} ]", uniqueId, error);
        settableFuture.setException(error);
    }
}
