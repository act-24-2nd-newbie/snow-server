package com.sds.todo.repository;

import com.sds.todo.model.Member;
import com.sds.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByMember(Member member);
}
