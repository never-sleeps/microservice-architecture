package com.microservicearchitecture.messaging;

public interface DomainEventHandler<T> {
    void process(T command);
}
