package com.drpweb.user;

import com.drpweb.role.Role;
import com.drpweb.role.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Asus on 7/8/2559.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {



    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User create(User user) {
        Role userRole = roleDao.findByRoleName(user.getRoles().iterator().next().getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return userDao.create(user);
    }

}
