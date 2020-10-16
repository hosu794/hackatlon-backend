package com.hackatlon.hackatlon.service;

import com.hackatlon.hackatlon.exception.AppException;
import com.hackatlon.hackatlon.exception.ResourceNotFoundException;
import com.hackatlon.hackatlon.model.Role;
import com.hackatlon.hackatlon.model.RoleName;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.*;
import com.hackatlon.hackatlon.repository.RoleRepository;
import com.hackatlon.hackatlon.repository.UserRepository;
import com.hackatlon.hackatlon.security.JwtTokenProvider;
import com.hackatlon.hackatlon.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class AuthenticationService  {


    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {



        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);



        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

    }


    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }



        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));



        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();


        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }

    public ResponseEntity<?> updateName(UserPrincipal currentUser, UpdateNameRequest
            updateNameRequest) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        if(userRepository.existsByName(updateNameRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Name already in user"), HttpStatus.BAD_REQUEST);
        }

        user.setName(updateNameRequest.getName());

        User result =  userRepository.save(user);

        return ResponseEntity.ok().body(new ApiResponse(true, "User name updated successfully"));

    }


    public ResponseEntity<?> updatePassword(UserPrincipal currentUser, UpdatePasswordRequest updatePasswordRequest) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(( ) -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));

        User result = userRepository.save(user);

        return ResponseEntity.ok().body(new ApiResponse(true, "User password updated successfully"));
    }

    public ResponseEntity<?> updateUsername(UserPrincipal currentUser, UpdateUsernameRequest updateUsernameRequest) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        if(userRepository.existsByUsername(updateUsernameRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username already in user"), HttpStatus.BAD_REQUEST);
        }

        user.setUsername(updateUsernameRequest.getUsername());

        User result = userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User username updated successfully"));
    }
}