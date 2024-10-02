package com.sds.todo.dto;

import java.time.Instant;
import java.util.Optional;

public class TaskDto {

    public record CreateTaskRequest(
            String contents
    ) {}

    public record FindTaskResponse(
            Long id,
            String contents,
            boolean isDone,
            Instant createdAt,
            Instant modifiedAt
    ) {}

    public record UpdateTaskRequest(
            String contents,
            Boolean isDone
    ) {}
}
