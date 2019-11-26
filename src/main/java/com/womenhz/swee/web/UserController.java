package com.womenhz.swee.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.womenhz.swee.model.User;
import com.womenhz.swee.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/get")
    User getById(@RequestParam Long id){
        return userService.getById(id);
    }

    @PostMapping("/create")
    Long create(@RequestBody User user) {
        return userService.create(user);
    }


}