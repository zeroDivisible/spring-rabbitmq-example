package io.zerodi.messaging.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.zerodi.messaging.amqp.RabbitMqConfiguration;
import io.zerodi.messaging.async.response.AsyncResponseConfiguration;

@Configuration
@ComponentScan(value = "io.zerodi.messaging")
@Import({RabbitMqConfiguration.class, AsyncResponseConfiguration.class})
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "messaging", ignoreUnknownFields = false)
public class MessagingConfiguration {

    /**
     * Number of concurrent consumers which will be used to consume messages from the work queues
     * <p/>
     * <p>
     * <b>Note:</b> be aware that having this number higher than <code>1</code> (or having more instances consuming messages) means that you
     * shouldn't rely on the ordering of messages as they might (and will) be processed in an unordered manner.
     * </p>
     */
    @Value("${concurrentConsumers:1}")
    private int concurrentConsumers;

    @Value("${mandatoryMessages:true}")
    private boolean mandatoryMessages;

    @Value("${responseExchangeName:response-exchange}")
    private String responseExchangeName;

    /**
     * Prefix of the name of the queue, on which we want the system to listen on incoming requests.
     */
    @Value("${responseQueuePrefix:responses}")
    private String responseQueuePrefix;

    @Bean
    protected MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(final int concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }

    public boolean isMandatoryMessages() {
        return mandatoryMessages;
    }

    public void setMandatoryMessages(final boolean mandatoryMessages) {
        this.mandatoryMessages = mandatoryMessages;
    }

    public String getResponseExchangeName() {
        return responseExchangeName;
    }

    public void setResponseExchangeName(final String responseExchangeName) {
        this.responseExchangeName = responseExchangeName;
    }

    public String getResponseQueuePrefix() {
        return responseQueuePrefix;
    }

    void setResponseQueuePrefix(final String responseQueuePrefix) {
        this.responseQueuePrefix = responseQueuePrefix;
    }
}
