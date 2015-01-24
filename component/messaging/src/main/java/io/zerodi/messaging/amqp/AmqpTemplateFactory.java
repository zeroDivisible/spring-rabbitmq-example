package io.zerodi.messaging.amqp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.MapRetryContextCache;
import org.springframework.retry.policy.RetryContextCache;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import io.zerodi.messaging.async.ResponseListener;
import io.zerodi.messaging.configuration.MessagingConfiguration;
import io.zerodi.messaging.core.UniqueId;
import io.zerodi.messaging.core.UniqueIdFactory;

@Primary
@Component
public class AmqpTemplateFactory implements FactoryBean<AmqpTemplate> {
    private static final Logger logger = LoggerFactory.getLogger(AmqpTemplateFactory.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private MessagingConfiguration messagingConfiguration;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private UniqueIdFactory uniqueIdFactory;

    @Autowired
    private ResponseListener responseListener;

    @Override
    public AmqpTemplate getObject() throws Exception {
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRetryTemplate(retryTemplate());

        if (messagingConfiguration.isMandatoryMessages()) {
            logger.info("will send mandatoryMessages, which expect at least one consumer");

            rabbitTemplate.setMandatory(true);
            rabbitTemplate.setReturnCallback(createReturnCallback());
        } else {
            logger.info("mandatory flag is not set, messages are not being set as mandatory");
        }

        return rabbitTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return AmqpTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private RabbitTemplate.ReturnCallback createReturnCallback() {
        return new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(final Message message, final int replyCode, final String replyText, final String exchange,
                                        final String routingKey) {
                try {
                    byte[] correlationFromMessage = message.getMessageProperties().getCorrelationId();
                    UniqueId uniqueId = uniqueIdFactory.fromBytes(correlationFromMessage);
                    logger.warn("exchange: [ {} ], routingKey: [ {} ], there is no consumer defined to consume [ {} ]", exchange,
                                routingKey, message);
                    responseListener.notifyWithError(uniqueId, new RuntimeException("no listener for message"));
                } catch (Exception e) {
                    logger.warn("for message {}, replyCode {}, replyText, exchange, routingKey", message, replyCode, replyText, exchange,
                                routingKey);
                    logger.warn("something is wrong while processing return callback", e);
                }
            }
        };
    }

    private RetryTemplate retryTemplate() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
        exceptionMap.put(Throwable.class, Boolean.TRUE);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());
        retryTemplate.setRetryContextCache(retryContextCache());
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3, exceptionMap));

        return retryTemplate;
    }

    private RetryContextCache retryContextCache() {
        return new MapRetryContextCache();
    }
}
