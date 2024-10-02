package com.sds.todo.repository;

import com.sds.todo.config.JpaConfig;
import com.sds.todo.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testCreate() {
        taskRepository.save(new Task(null, "Contents", false, null, null));

        var result = taskRepository.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertNotNull(result.get(0).getCreatedAt());
    }
}
