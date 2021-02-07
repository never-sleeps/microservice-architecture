package ru.otus.microservicearchitecture.homework02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.microservicearchitecture.homework02.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
