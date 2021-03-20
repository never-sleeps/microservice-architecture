package ru.microservicearchitecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservicearchitecture.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
