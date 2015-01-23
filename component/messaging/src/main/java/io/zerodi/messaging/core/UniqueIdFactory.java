package io.zerodi.messaging.core;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UniqueIdFactory {

    public UniqueId createUniqueId() {
        return new UuidUniqueId(UUID.randomUUID().toString());
    }

    public UniqueId fromBytes(byte[] uniqueId) {
        return new UuidUniqueId(uniqueId);
    }

    public UniqueId fromString(String uniqueId) {
        return new UuidUniqueId(uniqueId);
    }
}
