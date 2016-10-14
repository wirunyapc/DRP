package com.drpweb.user;


/**
 * Created by Asus on 7/8/2559.
 */
public interface UserService{

    User findByUserName(String username);
    User create(User user);

}
