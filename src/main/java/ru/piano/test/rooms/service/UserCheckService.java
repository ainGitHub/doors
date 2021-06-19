package ru.piano.test.rooms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.piano.test.rooms.model.Door;
import ru.piano.test.rooms.model.User;
import ru.piano.test.rooms.repository.DoorRepository;
import ru.piano.test.rooms.repository.UserRepository;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
public class UserCheckService {

    private final DoorRepository doorRepository;
    private final UserRepository userRepository;

    public UserCheckService(DoorRepository doorRepository, UserRepository userRepository) {
        this.doorRepository = doorRepository;
        this.userRepository = userRepository;
    }

    public boolean userCheck(Long doorId, Boolean entrance, Long keyId) {
        Assert.noNullElements(Arrays.asList(doorId, entrance, keyId), "Params must be not null");
        log.debug("Start find door = {}", doorId);
        Door door = doorRepository.getById(doorId);
        Assert.notNull(door, "Door not found");
        log.debug("Door = {} finded", doorId);

        log.debug("Start find user = {}", keyId);
        User user = userRepository.getById(keyId);
        Assert.notNull(user, "User not found");
        log.debug("User = {} finded", keyId);

        return userIdDivideToDoorId(user, door) && userEntranceCheck(user, entrance, door);
    }

    private boolean userIdDivideToDoorId(User user, Door door) {
        boolean divided = user.getId() % door.getId() == 0;
        log.debug("UserId = {} {} divided to doorId = {}", user.getId(), divided ? "" : "not", door.getId());
        return divided;
    }

    private boolean userEntranceCheck(User user, Boolean entrance, Door door) {
        if (entrance) {
            log.debug("Check user = {} can enter to room = {}", user.getId(), door.getId());
            return Objects.equals(user.getRoomId(), null);
        } else {
            log.debug("Check user = {} can out from room = {}", user.getId(), door.getId());
            return Objects.equals(user.getRoomId(), door.getId());
        }
    }
}
