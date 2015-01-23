package io.zerodi.messaging.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    protected ConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPublisherReturns(true);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean
    protected RabbitAdmin rabbitAdmin() throws Exception {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());

        rabbitAdmin.afterPropertiesSet();
        return rabbitAdmin;
    }
}
