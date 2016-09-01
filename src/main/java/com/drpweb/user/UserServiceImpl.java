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
/*    @Autowired
    private CalInfoService calInfoService;*/

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
        /*CalInfo calInfo = new CalInfo();
        calInfo.setBudget(calInfoService.calBudget(user));
        calInfo.setActivity(0.0);
        calInfo.setDiet(0.0);
        calInfo.setNet(0.0);
        calInfo.setUnder(0.0);
        user.setCalInfo(calInfo);*/
        return userDao.create(user);
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
