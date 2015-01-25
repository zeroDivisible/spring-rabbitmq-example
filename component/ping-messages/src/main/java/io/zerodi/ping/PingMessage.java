package io.zerodi.ping;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class PingMessage {

    private final String payload;
    private final Date   date;


    @JsonCreator
    public PingMessage(@JsonProperty(value = "payload") final String payload, @JsonProperty(value = "date") final Date date) {
        Preconditions.checkNotNull(payload, "payload cannot be null!");
        Preconditions.checkNotNull(date, "date cannot be null!");

        this.payload = payload;
        this.date = date;
    }

    public String getPayload() {
        return payload;
    }

    public Date getDate() {
        return date;
    }

    @Override public String toString() {
        return "PingMessage{" +
                "payload='" + payload + '\'' +
                ", date=" + date +
                '}';
    }
}
