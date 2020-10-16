package com.hackatlon.hackatlon.service;

import com.hackatlon.hackatlon.exception.ResourceNotFoundException;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.*;
import com.hackatlon.hackatlon.repository.UserRepository;
import com.hackatlon.hackatlon.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private final UserRepository userRepository;




    public UserSummary getCurrentUser(UserPrincipal currentUser) {


        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));


            return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());


    }


    public UserIdentityAvailability checkUsernameAvailability(UsernameRequest usernameRequest) {
        Boolean isAvailable = !userRepository.existsByUsername(usernameRequest.getUsername());

        return new UserIdentityAvailability(isAvailable);
    }

    public UserIdentityAvailability checkEmailAvailability(EmailRequest emailRequest) {
        Boolean isAvailable = !userRepository.existsByEmail(emailRequest.getEmail());

        return new UserIdentityAvailability(isAvailable);
    }

    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());

        return userProfile;
    }

}