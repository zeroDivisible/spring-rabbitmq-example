package io.zerodi.messaging.service;

public class MessagingException extends RuntimeException {
    private static final long serialVersionUID = 1734849934152282267L;

    public MessagingException(final Exception exception) {
        super(exception);
    }
}
