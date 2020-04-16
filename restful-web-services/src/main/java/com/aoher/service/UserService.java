package com.aoher.service;

import com.aoher.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    User findOne(int id);

    User deleteById(int id);

}
