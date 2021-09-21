package io.github.jeyhung.users.domain;

import io.github.jeyhung.users.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
