package com.sds.todo.model;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void test() {
        User u = User.builder()
                .id(null)
                .name(null)
                .password(null)
                .build();
    }
}
