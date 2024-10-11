package com.sds.todo.controller;

import com.sds.todo.dto.TaskDto;
import com.sds.todo.model.Task;
import com.sds.todo.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

//    public ResponseEntity<Void> createTaskV1(@RequestBody TaskDto.CreateTaskRequest request) {
//        // Check input data
//        if (request.contents() == null || request.contents().isBlank()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        taskService.createTask(request.contents());
//
//        return ResponseEntity.created(URI.create("")).build();
//    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDto.CreateTaskRequest request) {
        // Check input data
        if (request.memberId() == null ||
                request.contents() == null ||
                request.contents().isBlank()
        ) {
            return ResponseEntity.badRequest().build();
        }

        try {
            taskService.createTask(request.memberId(), request.contents());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/member/{memberId}")
    public List<TaskDto.FindTaskResponse> getTasks(@PathVariable Long memberId) {
        List<Task> tasks = taskService.getTasks(memberId);
        return tasks.stream().map((task) -> new TaskDto.FindTaskResponse(
                task.getId(),
                task.getContents(),
                task.getIsDone(),
                task.getCreatedDate(),
                task.getModifiedDate()
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
