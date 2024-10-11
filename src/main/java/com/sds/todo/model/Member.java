package com.sds.todo.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity(name = "members")
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @SequenceGenerator(name = "members_id_seq", sequenceName = "members_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "members_id_seq")
    private Long id;

    @Setter
    @Column(nullable = false, length = 200)
    private String email;

    @Setter
    @Column(nullable = false, length = 50)
    private String userName;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant signupDate;

    @Builder
    public Member(Long id) {
        this.id = id;
    }
}
