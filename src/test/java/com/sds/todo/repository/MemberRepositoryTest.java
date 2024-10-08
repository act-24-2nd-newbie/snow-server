package com.sds.todo.repository;

import com.sds.todo.config.JpaConfig;
import com.sds.todo.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createMemberTest() {
        // prepare data
        Member member = new Member();
        member.setEmail("test@email.com");
        member.setUserName("test");

        // save
        memberRepository.save(member);

        // check
        var result = memberRepository.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("test", result.get(0).getUserName());
        Assertions.assertEquals("test@email.com", result.get(0).getEmail());
    }

    @Test
    void findByEmailTest() {
        // prepare data
        Member member = new Member();
        member.setEmail("test@email.com");
        member.setUserName("test");

        // save
        memberRepository.save(member);

        var found = memberRepository.findByEmail("test@email.com");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("test@email.com", found.get().getEmail());

        var notFound = memberRepository.findByEmail("testx@email.com");
        Assertions.assertFalse(notFound.isPresent());
    }
}
