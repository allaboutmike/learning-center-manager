package com.learningcenter.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningcenter.dto.CreateSessionRequest;
import com.learningcenter.service.SessionService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = SessionController.class)
public class SessionControllerTest {

    @MockitoBean
    private SessionService sessionService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    void shouldCreateSession() throws Exception {
        CreateSessionRequest request = new CreateSessionRequest(5L, 11L,
                10L, "Mathematics", "Struggles with Algebra",
                LocalDateTime.now(), LocalDateTime.now());

        when(sessionService.createSession(any(CreateSessionRequest.class)))
                .thenReturn(request);

        mockMvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(5))
                .andExpect((jsonPath("$.tutorId").value(11)));
    }

    @Test
    void shouldReturnNotFoundWhenSessionDoesNotExist() throws Exception {
        when(sessionService.getSessionById(999L))
                .thenThrow(new SessionNotFoundException("Session not found"));

        mockMvc.perform(get("/api/sessions/999"))
                .andExpect(status().isNotFound());
    }
}
