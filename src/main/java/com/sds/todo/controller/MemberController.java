package com.sds.todo.controller;

import com.sds.todo.dto.MemberDto.CheckMemberRequest;
import com.sds.todo.dto.MemberDto.CheckMemberResponse;
import com.sds.todo.dto.MemberDto.CreateMemberRequest;
import com.sds.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/check")
    public Optional<CheckMemberResponse> checkMember(@RequestBody CheckMemberRequest request) {
        var member = memberService.checkMember(request.email());
        return member.map((m) -> new CheckMemberResponse(
                m.getId(), m.getEmail(), m.getUserName()
        ));
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody CreateMemberRequest request) {
        memberService.createMember(request.email(), request.userName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
