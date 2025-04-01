package com.whatsapp.backend.config.security;


import com.whatsapp.backend.common.JwtAuthFilter;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable) // CSRF'yi devre dışı bırakıyoruz (prod ortamında dikkat).
                .cors(Customizer.withDefaults()) //Dışarıdan erişim için cors aktif et
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated()) // Herhangi bir istek authenticated olmalı.

                .httpBasic(Customizer.withDefaults()).formLogin(formlogin->formlogin.disable());// HTTP Basic ile giriş.
       security.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        security
                /* JWT, stateless bir yapı olduğu için kimlik doğrulama işlemi için kullanılacak.
              Kullanıcı doğrulama işlemi için JWT kullanacağız, ancak mesaj şifreleme için SHA-256 kullanılacak.
              Bu nedenle, kimlik doğrulama için kullanılan `DaoAuthenticationProvider` ve ilgili filter'ı kaldırdım.
              JWT, stateless olması nedeniyle sunucu üzerinde oturum bilgisi tutmadan sadece token ile doğrulama işlemi yapılacak. */


                .sessionManagement(x
                        -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return security.build();
    }





}

