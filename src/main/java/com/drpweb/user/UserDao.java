package com.drpweb.user;

import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
public interface UserDao {
    List<User> findAll();
    User create(User user);
    User update(User user);
    void delete(User user);
    User findByUsername(String username);
}
