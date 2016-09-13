package com.drpweb.user;

import com.drpweb.diet_plan.DietPlanDao;
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
    @Autowired
    DietPlanDao dietPlanDao;

    public User currentUser;

    public UserServiceImpl() {
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

    @Override
    public void setCurrentUser(String username) {
        this.currentUser = findByUserName(username);
        System.out.print("current user : "+this.currentUser.getUsername());
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) { //used in test class
        this.userDao = userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }//used in test class
}
