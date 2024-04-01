package org.orgless.carboncellassignment.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.orgless.carboncellassignment.utils.AuthorizationDto;
import org.orgless.carboncellassignment.utils.LoginDto;
import org.orgless.carboncellassignment.utils.RegisterDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class AuthService {
    private final AuthenticationProvider authProvider;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String registerUser(RegisterDto registerDto) {
        final String username = registerDto.getEmail(), password = registerDto.getPassword();

        if (userDetailsManager.userExists(username))
            return "User is already registered";

        System.out.println("Registering new user");
        UserDetails user = User.builder()
                .username(username)
                .password(password)
                .passwordEncoder(passwordEncoder::encode)
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);

        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        System.out.printf("New User registered: { Email: \"%s\", Authorities: \"%s\" }%n", userDetails.getUsername(), userDetails.getAuthorities());
        System.out.println("Password -> " + userDetails.getPassword());

        return "New user registered: " + userDetails.getUsername();
    }

    public AuthorizationDto loginUser(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authentication = authProvider
                .authenticate(new UsernamePasswordAuthenticationToken(
                        email, password,
                        roles
                ));
        if(authentication == null)
            throw new BadCredentialsException("Invalid Email/Password");

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        final String jwtToken = jwtService.generateToken(
                User.builder()
                        .username(email)
                        .passwordEncoder(passwordEncoder::encode)
                        .password(password)
                        .roles("USER")
                        .build()
        );

        return new AuthorizationDto(jwtToken);
    }
}
