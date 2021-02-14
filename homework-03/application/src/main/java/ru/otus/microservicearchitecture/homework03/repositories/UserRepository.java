package ru.otus.microservicearchitecture.homework03.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.microservicearchitecture.homework03.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
