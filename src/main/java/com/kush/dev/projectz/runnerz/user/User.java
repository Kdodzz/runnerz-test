package com.kush.dev.projectz.runnerz.user;

public record User (
    Integer id,
    String name,
    String email,
    Address address,
    String phone,
    String website,
    Company company
){}
