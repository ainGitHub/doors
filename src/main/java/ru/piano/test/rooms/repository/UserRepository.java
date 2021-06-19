package ru.piano.test.rooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.piano.test.rooms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
