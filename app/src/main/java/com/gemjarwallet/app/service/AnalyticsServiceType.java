package com.gemjarwallet.app.service;

import com.gemjarwallet.app.entity.ServiceErrorException;

public interface AnalyticsServiceType<T> {

    void track(String eventName);

    void track(String eventName, T event);

    void flush();

    void identify(String uuid);

    void recordException(ServiceErrorException e);
}