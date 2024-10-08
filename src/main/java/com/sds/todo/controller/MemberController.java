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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/check")
    public CheckMemberResponse checkMember(@RequestBody CheckMemberRequest request) {
        String userName = memberService.checkMember(request.email());
        return new CheckMemberResponse(userName != null, userName);
    }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody CreateMemberRequest request) {
        memberService.createMember(request.email(), request.userName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
