package com.drpweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/all",method = RequestMethod.GET)
    public List<User> list(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public  User getByUsername(@RequestParam("name")String name){
        return userService.findByUserName(name);
    }
}
