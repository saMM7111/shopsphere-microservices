package com.samm7111.users.service;

import com.samm7111.users.model.UserAccount;
import com.samm7111.users.model.request.CreateUserRequest;
import com.samm7111.users.model.request.LoginRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InMemoryUserService implements UserService {

    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public InMemoryUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount create(CreateUserRequest request) {
        if (users.containsKey(request.email())) {
            throw new IllegalArgumentException("User already exists");
        }
        UserAccount user = new UserAccount(
            request.email(),
            request.fullName(),
            passwordEncoder.encode(request.password()),
            request.role().toUpperCase(),
            Instant.now());
        users.put(request.email(), user);
        return user;
    }

    @Override
    public List<UserAccount> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public UserAccount findByEmail(String email) {
        UserAccount user = users.get(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    @Override
    public boolean login(LoginRequest request) {
        UserAccount user = users.get(request.email());
        return user != null && passwordEncoder.matches(request.password(), user.encodedPassword());
    }
}