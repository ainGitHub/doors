package ru.piano.test.rooms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.piano.test.rooms.model.Door;

public interface DoorRepository extends JpaRepository<Door, Long> {
}
