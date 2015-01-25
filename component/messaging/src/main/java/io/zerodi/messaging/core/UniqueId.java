package io.zerodi.messaging.core;

/**
 * Simple "unique-ish" id.
 */
public interface UniqueId {

    public String asString();

    public byte[] asByteArray();
}
