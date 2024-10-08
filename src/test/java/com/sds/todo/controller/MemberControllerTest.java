package com.sds.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.todo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    void testCheckMemberNotExist() throws Exception {
        when(memberService.checkMember("notexist@email.com")).thenReturn(null);

        ObjectMapper mapper = new ObjectMapper();
        var input = Map.of("email", "notexist@email.com");

        mockMvc.perform(post("/api/v1/members/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(false));

        verify(memberService, times(1)).checkMember("notexist@email.com");
    }

    @Test
    void testCheckMemberExist() throws Exception {
        when(memberService.checkMember("exist@email.com")).thenReturn("exist");

        ObjectMapper mapper = new ObjectMapper();
        var input = Map.of("email", "exist@email.com");

        mockMvc.perform(post("/api/v1/members/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(true));

        verify(memberService, times(1)).checkMember("exist@email.com");
    }

    @Test
    void testCreateMember() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        var input = Map.of("email", "test@email.com", "userName", "test");

        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                )
                .andExpect(status().isCreated());

        verify(memberService, times(1)).createMember("test@email.com", "test");
    }
}
