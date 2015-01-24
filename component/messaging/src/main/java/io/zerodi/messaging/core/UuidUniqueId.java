package io.zerodi.messaging.core;

import java.nio.charset.Charset;

import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;

class UuidUniqueId implements UniqueId {

    private static final Charset ENCODING = Charset.forName("UTF-8");

    private final String uuid;

    public UuidUniqueId(final String uuid) {
        Preconditions.checkArgument(StringUtils.hasText(uuid), "StringUtils.hasText(uuid) is not fulfilled!");
        this.uuid = uuid;

    }

    UuidUniqueId(byte[] id) {
        Preconditions.checkNotNull(id, "id cannot be null!");
        this.uuid = new String(id, ENCODING);
    }

    @Override
    public String asString() {
        return uuid;
    }

    @Override
    public byte[] asByteArray() {
        return uuid.getBytes(ENCODING);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UuidUniqueId id = (UuidUniqueId) o;

        return uuid.equals(id.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
