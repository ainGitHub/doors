package ru.piano.test.rooms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.piano.test.rooms.service.UserCheckService;

@Slf4j
@RestController
public class UserCheckController {

    private final UserCheckService userCheckService;

    public UserCheckController(UserCheckService userCheckService) {
        this.userCheckService = userCheckService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUser(@RequestParam("roomId") Long roomId,
                                       @RequestParam("entrance") Boolean entrance,
                                       @RequestParam("keyId") Long keyId) {
        log.info("Start to check roomId = {}, entrance = {}, keyId = {}", roomId, entrance, keyId);
        if (userCheckService.userCheck(roomId, entrance, keyId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Exception when check user", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
