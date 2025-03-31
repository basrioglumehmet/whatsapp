package com.whatsapp.backend.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class PasswordEncoderConfig  {
    // The length of the salt used in the password encoding process.
    // A longer salt makes it harder to perform precomputed attacks (e.g., rainbow table attacks).
    @Value("${application.security.saltlength}")
    private int saltLength;

    // The length of the hash generated during password encoding.
    // A longer hash generally increases security by making the hash more resistant to brute-force attacks.
    @Value("${application.security.hashlength}")
    private int hashLength;

    // The number of threads to use for parallel processing in the password encoding process.
    // A higher thread count can speed up the encoding process but requires more system resources.
    @Value("${application.security.threadsize}")
    private int thread;

    // The memory cost used in the password encoding process (in kilobytes).
    // A higher memory size increases the computational effort needed to compute the hash, improving security.
    @Value("${application.security.memorysize}")
    private int memory;

    // The number of iterations used in the hashing algorithm.
    // More iterations mean increased resistance to brute-force attacks, but at the cost of performance.
    @Value("${application.security.iterations}")
    private int iterations;
    @Bean
    public  PasswordEncoder getPasswordEncoder() {
        return new Argon2PasswordEncoder(saltLength,hashLength,thread,memory,iterations);
    }
}
