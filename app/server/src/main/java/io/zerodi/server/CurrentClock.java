package io.zerodi.server;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CurrentClock implements Clock {

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
