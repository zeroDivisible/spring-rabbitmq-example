package io.zerodi.messaging.async;

/**
 * When dealing with AMQP, spring allows us to use an adapter (
 * {@link org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter}, which can be instantiated with a delegate object, to
 * which the adapter will be passing incoming requests, expecting an object being sent back, which will be serialized and sent as the
 * response. Spring is the doing some "guessing game" on finding specific method in the delegate, which should be called to handle the
 * message - implementing this interface enforces the presence of that method (<code>handleMessage</code>)
 *
 * @author zerodi
 */
public interface MessageHandler<Request, Response> {

    Response handleMessage(Request request);
}
