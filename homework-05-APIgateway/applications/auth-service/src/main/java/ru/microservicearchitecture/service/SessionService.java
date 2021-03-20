package ru.microservicearchitecture.service;

import ru.microservicearchitecture.model.Session;
import ru.microservicearchitecture.model.User;

public interface SessionService {
    Session saveSessionForUser(User user);

    Session findSession(String sessionId);

    void deleteSession(String sessionId);
}
