package ru.piano.test.rooms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.piano.test.rooms.model.Door;
import ru.piano.test.rooms.model.User;
import ru.piano.test.rooms.repository.DoorRepository;
import ru.piano.test.rooms.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserCheckServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoorRepository doorRepository;

    @InjectMocks
    private UserCheckService userCheckService;

    @Test
    public void userCheckArgumentValues() {
        checkArguments(null, null, null, null);
    }

    @Test
    public void userCheckIfDoorNotExists() {
        checkArguments(1L, 1L, true, null);
    }

    @Test
    public void userCheckIfUserNotExists() {
        checkArguments(1L, 1L, true, new Door());
    }

    private void checkArguments(Long roomId, Long userId, Boolean entrance, Door door) {
        when(doorRepository.getById(roomId)).thenReturn(door);
        assertThrows(IllegalArgumentException.class, () -> userCheckService.userCheck(roomId, entrance, userId));
    }

    @Test
    public void userCheck() {
        boolean result = checkUser(1L, 1L, true, new Door(1L), new User(1L));
        assertTrue(result, "Пользователь хочет зайти и он сам не в комнате, его номер делится на номер комнаты");
    }

    @Test
    public void userCheckOutNotFromRoom() {
        boolean result = checkUser(1L, 1L, false, new Door(1L), new User(1L));
        assertFalse(result, "Пользователь хочет выйти и он сам не в комнате, его номер делится на номер комнаты");
    }

    @Test
    public void userTryToInButNumberNotDivided() {
        boolean result = checkUser(5L, 6L, true, new Door(5L), new User(6L));
        assertFalse(result, "Пользователь хочет зайти и он сам не в комнате, его номер не делится на номер комнаты");
    }

    @Test
    public void userTryToOutButNotInRoom() {
        boolean result = checkUser(1L, 1L, false, new Door(1L), new User(1L));
        assertFalse(result, "Пользователь хочет выйти и он сам не в комнате, его номер делится на номер комнаты");
    }

    @Test
    public void userTryToOutFromRoom() {
        boolean result = checkUser(1L, 1L, false, new Door(1L), new User(1L, 1L));
        assertTrue(result, "Пользователь хочет выйти и он сам не в комнате, его номер делится на номер комнаты");
    }

    private boolean checkUser(Long roomId, Long userId, Boolean entrance, Door door, User user) {
        when(doorRepository.getById(any())).thenReturn(door);
        when(userRepository.getById(any())).thenReturn(user);

        return userCheckService.userCheck(roomId, entrance, userId);
    }
}