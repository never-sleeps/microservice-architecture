package ru.microservicearchitecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservicearchitecture.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
