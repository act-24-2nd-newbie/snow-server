package com.sds.todo.dto;

public class MemberDto {

    public record CheckMemberRequest(
            String email
    ) {}

    public record CheckMemberResponse(
            boolean exists,
            String userName
    ) {}

    public record CreateMemberRequest(
            String email,
            String userName
    ) {}
}
