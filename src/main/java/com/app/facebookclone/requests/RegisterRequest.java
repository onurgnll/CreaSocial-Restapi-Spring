package com.app.facebookclone.requests;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String surname;
    private String email;
    private String password;

}
