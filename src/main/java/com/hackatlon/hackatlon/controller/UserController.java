package com.hackatlon.hackatlon.controller;

import com.hackatlon.hackatlon.payload.*;
import com.hackatlon.hackatlon.security.CurrentUser;
import com.hackatlon.hackatlon.security.UserPrincipal;
import com.hackatlon.hackatlon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }


    private final UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return  userService.getCurrentUser(currentUser);
    }

    @PostMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestBody UsernameRequest usernameRequest) {

        return userService.checkUsernameAvailability(usernameRequest);

    }

    @PostMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestBody EmailRequest emailRequest) {
        return userService.checkEmailAvailability(emailRequest);
    }



    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        return userService.getUserProfile(username);
    }


}