package com.daniel.assignment.controller;

import com.daniel.assignment.dao.ApplicationUserDao;
import com.daniel.assignment.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ApplicationUserDao applicationUserDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserDao applicationUserDao,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserDao = applicationUserDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserDao.save(user);
    }
}