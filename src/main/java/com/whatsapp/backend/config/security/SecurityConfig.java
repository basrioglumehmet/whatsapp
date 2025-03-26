package com.whatsapp.backend.config.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Araştırma konusu: Spring Security Filter Chain.
//Session policy nedir? Araştır öğren.
//Araştırma konusu: UserDetailsService nedir öğren.
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable) // CSRF'yi devre dışı bırakıyoruz (prod ortamında dikkat).
                .cors(Customizer.withDefaults()) //Dışarıdan erişim için cors aktif et
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated()) // Herhangi bir istek authenticated olmalı.

                .httpBasic(Customizer.withDefaults()); // HTTP Basic ile giriş.
       // security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        security.authenticationProvider(authenticationProvider()); // Custom authentication provider'ı tanıtıyoruz.
        security.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // Stateless oturum yönetimi.

        return security.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      // provider.setPasswordEncoder(customAesEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    //Authenticaton manager arkaplanda kendi işini yapıyor fakat biz token bazlı yapmak istediğimiz için benim handle etmeme izin ver diyoruz.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); //Authentication Manager getirmenin ilk yoludur
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return customAesEncoder;
//    }

}

