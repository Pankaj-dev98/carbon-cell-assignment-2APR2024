package org.orgless.carboncellassignment.configs.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService(UserDetailsManager userDetailsManager) {
        userDetailsManager.createUser(
                User.builder()
                        .username("admin@carboncell.com")
                        .password(passwordEncoder().encode("admin"))
                        .roles("ADMIN")
                        .build()
        );

        userDetailsManager.createUser(
                User.builder()
                        .username("user@carboncell.com")
                        .password(passwordEncoder().encode("user"))
                        .roles("USER")
                        .build()
        );
        return userDetailsManager;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        System.out.println(config.getAuthenticationManager().getClass());
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }


}
