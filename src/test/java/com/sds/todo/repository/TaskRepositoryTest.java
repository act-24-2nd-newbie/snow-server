package com.sds.todo.repository;

import com.sds.todo.config.JpaConfig;
import com.sds.todo.model.Member;
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
    private MemberRepository memberRepository;

    @Autowired
    private TaskRepository taskRepository;

    Member createMember() {
        // prepare data
        Member member = new Member();
        member.setEmail("test@email.com");
        member.setUserName("test");

        // save and return
        return memberRepository.save(member);
    }

    @Test
    void testCreate() {
        var member = createMember();

        Task task = new Task();
        task.setContents("Contents");
        task.setIsDone(false);
        task.setMember(member);
        taskRepository.save(task);

        var result = taskRepository.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Contents", result.get(0).getContents());
        Assertions.assertEquals(false, result.get(0).getIsDone());
        Assertions.assertNotNull(result.get(0).getCreatedDate());
    }
}
