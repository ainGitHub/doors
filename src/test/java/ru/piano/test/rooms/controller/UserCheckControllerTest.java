package ru.piano.test.rooms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.piano.test.rooms.service.UserCheckService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCheckController.class)
public class UserCheckControllerTest {

    @MockBean
    private UserCheckService userCheckService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkUser() throws Exception {
        when(userCheckService.userCheck(1L, false, 100L)).thenReturn(true);

        mockMvc.perform(get("/check")
                .queryParam("roomId", "1")
                .queryParam("entrance", "false")
                .queryParam("keyId", "100"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkUserWithIllegalArguments() throws Exception {
        mockMvc.perform(get("/check"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void checkUserWhenNotAllowed() throws Exception {
        when(userCheckService.userCheck(1L, false, 100L)).thenReturn(false);

        mockMvc.perform(get("/check")
                .queryParam("roomId", "1")
                .queryParam("entrance", "false")
                .queryParam("keyId", "100"))
                .andExpect(status().is4xxClientError());
    }
}
