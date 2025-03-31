package com.whatsapp.backend.service.impl;

import com.whatsapp.backend.common.Jwtutil;
import com.whatsapp.backend.dao.model.Account;
import com.whatsapp.backend.dao.model.Role;
import com.whatsapp.backend.dao.repo.AccountRepo;
import com.whatsapp.backend.dto.LoginRequest;
import com.whatsapp.backend.dto.RegisterAccountRequest;
import com.whatsapp.backend.dto.RegisterAccountResponse;
import com.whatsapp.backend.mapper.Usermapper;
import com.whatsapp.backend.config.security.PasswordEncoderConfig;
import com.whatsapp.backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor // Lombok generates a constructor for all fields in the class
public class AuthServiceImpl implements AuthService {
    // The constructor is automatically generated by Lombok.
    // Spring will inject the 'Usermapper' dependency through this constructor.
    private final Usermapper usermapper;
    private final AccountRepo accountRepo;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final PasswordEncoderConfig config;
    private final Jwtutil jwtutil;


    @Override
    public RegisterAccountResponse register(RegisterAccountRequest registerAccountRequest) {
        Account account = usermapper.mapRegisterRequestToAccount(registerAccountRequest);
        account.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(account.getPassword()));
        account.setRole(Collections.singletonList(Role.ROLE_USER));
        Account registeredAccount = accountRepo.save(account);
        return usermapper.mapAccountToRegisterAccountResponse(registeredAccount);
    }
    @Override
    public String login(LoginRequest loginRequest){
        UserDetails user = accountRepo.getAccountsByUsername(loginRequest.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));

        // Kullanıcı kontrolü
        if (user == null || !passwordEncoderConfig.getPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Geçersiz kullanıcı adı veya şifre!");
        }



        // JWT oluştur ve döndür
        return jwtutil.generateToken(user.getUsername());
    }

}
