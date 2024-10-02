package com.sds.todo.service;

import com.sds.todo.model.Task;
import com.sds.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public void createTask(String contents) {
        taskRepository.save(Task.builder().contents(contents).isDone(false).build());
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public void updateTask(Long id, String contents, Boolean isDone) {
        var target = taskRepository.findById(id);
        var task = target.orElseThrow(() -> new RuntimeException("Task not found"));
        if (contents != null) {
            task.setContents(contents);
        }
        if (isDone != null) {
            task.setIsDone(isDone);
        }
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void deleteTasks() {
        taskRepository.deleteAll();
    }
}
