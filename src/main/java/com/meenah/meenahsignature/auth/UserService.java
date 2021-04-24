package com.meenah.meenahsignature.auth;


import com.meenah.meenahsignature.exception.AppException;
import com.meenah.meenahsignature.exception.ResourcesNotFoundException;
import com.meenah.meenahsignature.payload.ApiResponse;
import com.meenah.meenahsignature.payload.SignUpRequest;
import com.meenah.meenahsignature.role.Role;
import com.meenah.meenahsignature.role.RoleName;
import com.meenah.meenahsignature.role.RoleRepository;
import com.meenah.meenahsignature.security.PasswordConfig;
import com.meenah.meenahsignature.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final PasswordConfig passwordEncoder;
    private final RoleRepository roleRepository;
    private final Validator validator;


    public ResponseEntity<?> singUp(SignUpRequest signUpRequest){

        boolean isValidEmail = validator.validateEmail(signUpRequest.getEmail());
        if (!isValidEmail){
            return new ResponseEntity(new ApiResponse(false, "Email is not valid"),
                                      HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                                      HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                                      HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                             signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.isAdmin());

        String encodedPassword = passwordEncoder.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedAt(Instant.now());

        Role userRole = roleRepository.findByName(RoleName.USER)
                                      .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users/{username}")
            .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    public User updateUser(User user){

        return userRepository.findById(user.getId()).map((u)->{
            u.setEmail(user.getEmail());
            u.setUpdatedAt(Instant.now());
            return userRepository.save(u);
        }).orElseThrow(() -> new ResourcesNotFoundException("User not found"+user.getId()));
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
