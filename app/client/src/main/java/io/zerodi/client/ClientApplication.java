package io.zerodi.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.zerodi.client.configuration.ClientConfiguration;
import io.zerodi.environment.configuration.EnvironmentConfiguration;
import io.zerodi.ping.configuration.PingConfiguration;

@Configuration
@EnableAutoConfiguration
@Import({ClientConfiguration.class, PingConfiguration.class, EnvironmentConfiguration.class})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientConfiguration.class, args);
    }
}
