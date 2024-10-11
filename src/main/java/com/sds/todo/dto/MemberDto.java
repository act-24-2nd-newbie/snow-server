package com.sds.todo.dto;

public class MemberDto {

    public record CheckMemberRequest(
            String email
    ) {}


    public record CheckMemberResponse(
            Long id,
            String email,
            String userName
    ) {}

    public record CreateMemberRequest(
            String email,
            String userName
    ) {}
}
