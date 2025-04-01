package com.whatsapp.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccountRequest {

    private String username;
    private String email;
    private String phone;
    private String password;

}
