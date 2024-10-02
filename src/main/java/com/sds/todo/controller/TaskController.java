package com.sds.todo.controller;

import com.sds.todo.dto.TaskDto;
import com.sds.todo.model.Task;
import com.sds.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDto.CreateTaskRequest request) {
        // Check input data
        if (request.contents() == null || request.contents().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        taskService.createTask(request.contents());

        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping
    public List<TaskDto.FindTaskResponse> getTasks() {
        List<Task> tasks = taskService.getTasks();
        return tasks.stream().map((task) -> new TaskDto.FindTaskResponse(
                task.getId(),
                task.getContents(),
                task.getIsDone(),
                task.getCreatedAt(),
                task.getModifiedAt()
        )).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putTask(@PathVariable Long id, @RequestBody TaskDto.UpdateTaskRequest request) {
        // Check input data
        if (request.contents() != null && request.contents().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        taskService.updateTask(id, request.contents(), request.isDone());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @DeleteMapping
    public void deleteTasks() {
        taskService.deleteTasks();
    }
}
