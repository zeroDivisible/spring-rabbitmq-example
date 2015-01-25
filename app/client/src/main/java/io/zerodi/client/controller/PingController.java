package io.zerodi.client.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.zerodi.client.configuration.ClientConfiguration;
import io.zerodi.client.service.PingService;
import io.zerodi.ping.PingMessage;

@RestController
public class PingController {

    @Autowired
    private PingService pingService;

    @Autowired
    private ClientConfiguration clientConfiguration;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public PingMessage pingMessage(@RequestParam(value = "message", required = false) String pingMessage) throws InterruptedException, ExecutionException, TimeoutException {
        Future<PingMessage> responseFuture = pingService.sendPing(pingMessage);

        return responseFuture.get(clientConfiguration.getPingResponseTimeout(), TimeUnit.MILLISECONDS);
    }
}
