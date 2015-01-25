package io.zerodi.client.service;

import java.util.concurrent.Future;

import io.zerodi.ping.PingMessage;

public interface PingService {

    Future<PingMessage> sendPing(String message);
}
