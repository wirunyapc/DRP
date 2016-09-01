package com.drpweb.user;


import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
public interface UserService{
    List<User> findAll();
    User findByUserName(String username);
    User create(User user);
}
