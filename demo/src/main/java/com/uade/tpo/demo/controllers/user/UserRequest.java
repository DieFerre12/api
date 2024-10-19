package com.uade.tpo.demo.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String password;
}
