package com.samm7111.users.service;

import com.samm7111.users.model.UserAccount;
import com.samm7111.users.model.request.CreateUserRequest;
import com.samm7111.users.model.request.LoginRequest;
import java.util.List;

public interface UserService {
    UserAccount create(CreateUserRequest request);
    List<UserAccount> findAll();
    UserAccount findByEmail(String email);
    boolean login(LoginRequest request);
}