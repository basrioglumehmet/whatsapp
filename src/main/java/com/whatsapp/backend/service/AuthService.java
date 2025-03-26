package com.whatsapp.backend.service;

import com.whatsapp.backend.dto.LoginRequest;
import com.whatsapp.backend.dto.RegisterAccountRequest;
import com.whatsapp.backend.dto.RegisterAccountResponse;

public interface AuthService {
    String login(LoginRequest loginRequest);
    RegisterAccountResponse register(RegisterAccountRequest registerAccountRequest);

}
