package io.zerodi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.zerodi.environment.configuration.EnvironmentConfiguration;
import io.zerodi.ping.configuration.PingConfiguration;
import io.zerodi.server.configuration.ServerConfiguration;

@Configuration
@EnableAutoConfiguration
@Import({ServerConfiguration.class, PingConfiguration.class, EnvironmentConfiguration.class,})
public class ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
