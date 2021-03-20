package ru.microservicearchitecture.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.microservicearchitecture.model.Session;
import ru.microservicearchitecture.model.User;
import ru.microservicearchitecture.repository.SessionRepository;
import ru.microservicearchitecture.service.SessionService;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Session saveSessionForUser(User user) {
        Session session = Session.builder()
                .userId(user.getId())
                .build();
        return sessionRepository.save(session);
    }

    @Override
    public Session findSession(String sessionId) {
        return sessionRepository.findById(Long.parseLong(sessionId)).orElse(null);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionRepository.deleteById(Long.parseLong(sessionId));
    }
}
