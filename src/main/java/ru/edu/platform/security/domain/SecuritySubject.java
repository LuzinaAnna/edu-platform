package ru.edu.platform.security.domain;

import lombok.Data;

@Data

public class SecuritySubject {
    private Long id;
    private String username;
    private String pwd;
    private String email;
    private boolean expired = false;
    private boolean locked = false;
    private boolean pwdExpired = false;
    private boolean enabled = true;
}
