package com.samm7111.users.controller;

import com.samm7111.users.model.UserAccount;
import com.samm7111.users.model.request.CreateUserRequest;
import com.samm7111.users.model.request.LoginRequest;
import com.samm7111.users.model.response.AuthResponse;
import com.samm7111.users.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserAccount> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        boolean valid = userService.login(request);
        if (!valid) {
            return ResponseEntity.badRequest().body(new AuthResponse(request.email(), "UNKNOWN", "Invalid credentials"));
        }
        UserAccount user = userService.findByEmail(request.email());
        return ResponseEntity.ok(new AuthResponse(user.email(), user.role(), "Login successful"));
    }

    @GetMapping("/view/{email}")
    public ResponseEntity<UserAccount> viewByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserAccount>> allUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}