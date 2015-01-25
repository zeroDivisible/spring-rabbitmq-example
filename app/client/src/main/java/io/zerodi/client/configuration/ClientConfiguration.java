package io.zerodi.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "io.zerodi.client")
public class ClientConfiguration {

    @Value("${pingResponseTimeout:10000}")
    private int pingResponseTimeout;

    public int getPingResponseTimeout() {
        return pingResponseTimeout;
    }

    public void setPingResponseTimeout(final int pingResponseTimeout) {
        this.pingResponseTimeout = pingResponseTimeout;
    }
}
