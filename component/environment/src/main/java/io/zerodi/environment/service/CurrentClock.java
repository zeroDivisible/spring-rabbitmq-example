package io.zerodi.environment.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
class CurrentClock implements Clock {

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
