package com.drpweb.user;

import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.role.Role;
import com.drpweb.role.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    UserService userService;


    public UserServiceImpl() {
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



    public void setUserDao(UserDao userDao) { //used in test class
        this.userDao = userDao;
    }//used in test class



    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }//used in test class
}
