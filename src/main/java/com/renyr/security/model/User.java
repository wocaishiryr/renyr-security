package com.renyr.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class User {

    private String userId;
    private String username;
    private String password;
    private int age;
    private long createTime;

    public User(String username, String s, List<GrantedAuthority> authorities) {
    }
}
