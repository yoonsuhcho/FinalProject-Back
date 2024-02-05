package com.example.securityjwt.model.entity;


import com.example.securityjwt.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;

    @Setter @Column(nullable = true) String userid;

    @Setter @Column(nullable = false) String email;

    @Setter @Column(nullable = false) String username;
    @Setter @Column(nullable = false) private String password;
    @Column(nullable = false) @Enumerated(EnumType.STRING) private UserRole role = UserRole.ROLE_USER;
    @Column(name = "register_at", updatable = false) private Timestamp registeredAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    protected UserEntity() {}

    private UserEntity(String userid, String username, String password, String email) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static UserEntity of(String userid, String username, String password, String email) {
        return new UserEntity(userid, username, password, email);
    }
}
