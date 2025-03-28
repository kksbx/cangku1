package org.tinkerhub.offgo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinkerhub.offgo.entity.User;

@RestController
public class registercontroller {
    @RequestMapping("/sign_in_{ID}_{password}")
    public boolean sign_in_ID(String ID, String password) {
        return true;
    }
    @RequestMapping("/sign_in_{username}_{password}")
    public boolean sign_in_username(String username, String password) {
        return true;
    }
    @RequestMapping("/sign_up_{username}_{password}")
    public boolean sign_up_username(String username, String password) {
        new User(username, password);
        return true;
    }
}
