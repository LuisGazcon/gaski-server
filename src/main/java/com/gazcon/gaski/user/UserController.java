package com.gazcon.gaski.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {
    
    @Autowired()
    UserService userService;

    @GetMapping("/email-exist")
    public Map<String, Boolean> emailExist(@RequestParam() String email) throws Exception {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        if(email.isEmpty()) {
            throw new IllegalArgumentException("the email field must be provided");
        } else {
            map.put("emailExist", userService.emailExist(email));
        }
        return map;
    }

    @GetMapping("/username-exist")
    public Map<String, Boolean> usernameExist(@RequestParam() String username) throws Exception {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        if(username.isEmpty()) {
            throw new IllegalArgumentException("the username field must be provided");
        } else {
            map.put("usernameExist", userService.usernameExist(username));
        }
        return map;     
    }

    @PostMapping()
    public Map<String, String> create(@RequestBody() Map<String, String> data) {
        return data;
    }
}
