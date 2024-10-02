package com.sds.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String password;
    private Instant createdAt;
    private Instant updateAt;

    @Builder
    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
