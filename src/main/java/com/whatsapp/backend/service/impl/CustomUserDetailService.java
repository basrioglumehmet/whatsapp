package com.whatsapp.backend.service.impl;

import com.whatsapp.backend.dao.repo.AccountRepo;
import com.whatsapp.backend.dao.repo.ProfileRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private AccountRepo accountRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return accountRepo.getAccountsByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
    }
}
