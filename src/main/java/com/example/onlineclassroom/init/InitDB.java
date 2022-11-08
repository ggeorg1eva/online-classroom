package com.example.onlineclassroom.init;

import com.example.onlineclassroom.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public InitDB(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
