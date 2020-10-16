package com.hackatlon.hackatlon.controller;

import com.hackatlon.hackatlon.payload.*;
import com.hackatlon.hackatlon.security.CurrentUser;
import com.hackatlon.hackatlon.security.UserPrincipal;
import com.hackatlon.hackatlon.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private final AuthenticationService authenticationService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticationService.registerUser(signUpRequest);
    }

    @PutMapping("/update/name")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateName(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UpdateNameRequest updateNameRequest) {
        return authenticationService.updateName(currentUser, updateNameRequest);
    }

    @PutMapping("/update/password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return authenticationService.updatePassword(currentUser, updatePasswordRequest);
    }

    @PutMapping("/update/username")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUsername(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UpdateUsernameRequest updateUsernameRequest) {
        return authenticationService.updateUsername(currentUser, updateUsernameRequest);
    }

}