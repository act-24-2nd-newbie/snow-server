package com.sds.todo.dto;

import java.time.Instant;

public class TaskDto {

    public record CreateTaskRequest(
            Long memberId,
            String contents
    ) {}

    public record FindTaskResponse(
            Long id,
            String contents,
            boolean isDone,
            Instant createdDate,
            Instant modifiedDate
    ) {}

    public record UpdateTaskRequest(
            String contents,
            Boolean isDone
    ) {}
}
