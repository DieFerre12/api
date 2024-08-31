package com.uade.tpo.demo.controllers.user;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String password;
}
