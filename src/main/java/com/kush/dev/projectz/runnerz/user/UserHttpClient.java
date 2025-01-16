package com.kush.dev.projectz.runnerz.user;

import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserHttpClient {

    @GetExchange("/users/{id}")
    User findById(int id);

    @GetExchange("/users")
    List<User> findAll();
}
