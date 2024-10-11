package com.sds.todo.service;

import com.sds.todo.model.Member;
import com.sds.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> checkMember(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public void createMember(String email, String userName) {
        Member member = new Member();
        member.setEmail(email);
        member.setUserName(userName);

        memberRepository.save(member);
    }
}
