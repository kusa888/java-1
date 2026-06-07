package com.example.clubmanagement;

import com.example.clubmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClubManagementApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ClubManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (!userService.existsByUsername("admin")) {
            userService.registerAdmin("admin", "123456");
            System.out.println("默认管理员账号已创建: admin / 123456");
        }
    }
}