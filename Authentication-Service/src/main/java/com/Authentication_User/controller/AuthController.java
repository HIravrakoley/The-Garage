package com.Authentication_User.controller;

import com.Authentication_User.model.User;
import com.Authentication_User.repository.UserRepo;
import com.Authentication_User.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private  static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    private final AuthService authService;
    private final UserRepo userRepo;



    public AuthController(AuthService authService, UserRepo userRepo) {
        this.authService = authService;
        this.userRepo = userRepo;
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody User user){
        return authService.registerUser(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody User user){
      String token = authService.loginUser(user.getUsername(),user.getPassword());
      Double latitude = user.getLatitude();
      Double longitude = user.getLongitude();
      LOGGER.info(String.valueOf(latitude));
      LOGGER.info(String.valueOf(longitude));
        if (token.equals("Invalid username or Password!"))
        {
    return ResponseEntity.status(401).body(token);
        }
        LOGGER.info("User logged in");
        return ResponseEntity.ok(token);

    }
}
