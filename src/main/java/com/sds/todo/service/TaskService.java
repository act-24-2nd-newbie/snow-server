package com.sds.todo.service;

import com.sds.todo.model.Task;
import com.sds.todo.repository.MemberRepository;
import com.sds.todo.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createTask(Long memberId, String contents) {
        var member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new EntityNotFoundException();
        }

        taskRepository.save(Task.builder()
                .member(member.get())
                .contents(contents)
                .isDone(false)
                .build()
        );
    }

    @Transactional(readOnly = true)
    public List<Task> getTasks(Long memberId) {
        var member = memberRepository.getReferenceById(memberId);
        return taskRepository.findAllByMember(member);
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
