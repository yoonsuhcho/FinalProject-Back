package com.example.securityjwt.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinRequest {
    private String userid;
    private String username;
    private String password;
    private String email;
}
