package com.sds.todo.controller;

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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"contents\":\"test\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetList() throws Exception {
        when(taskService.getTasks()).thenReturn(List.of(
                Task.builder()
                        .id(1L)
                        .contents("contents")
                        .isDone(false)
                        .createdDate(Instant.now())
                        .modifiedDate(Instant.now())
                        .build()
                )
        );

        mockMvc.perform(get("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contents\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1}]"));

        verify(taskService, times(1))
                .getTasks();
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
