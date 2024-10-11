package com.sds.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.todo.model.Member;
import com.sds.todo.model.Task;
import com.sds.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    public void testPost() throws Exception {
        var input = Map.of("memberId", 1L, "contents", "test");

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(input)))
                .andExpect(status().isCreated());

        verify(taskService, times(1))
                .createTask(1L, "test");
    }

    @Test
    public void testGetList() throws Exception {
        Member m = new Member();
        when(taskService.getTasks(1L)).thenReturn(List.of(
                Task.builder()
                        .id(1L)
                        .member(m)
                        .contents("contents")
                        .isDone(false)
                        .createdDate(Instant.now())
                        .modifiedDate(Instant.now())
                        .build()
                )
        );

        mockMvc.perform(get("/api/v1/tasks/member/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(taskService, times(1))
                .getTasks(1L);
    }

    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/api/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contents\":\"test\", \"isDone\":\"false\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1))
                .updateTask(eq(1L), eq("test"), eq(false));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(eq(1L));
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTasks();
    }
}
