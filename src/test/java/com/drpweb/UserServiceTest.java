package com.drpweb;

import com.drpweb.role.Role;
import com.drpweb.role.RoleDao;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import com.drpweb.user.UserServiceImpl;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Asus on 9/8/2559.
 */
public class UserServiceTest {
    @Test
     public void findByUserName(){
        User user = mock(User.class);
        when(user.getName()).thenReturn("Lita");
        UserDao dao = mock(UserDao.class);
        when(dao.findByUsername("Lita")).thenReturn(user);
        when(dao.findByUsername("")).thenReturn(null);

        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(dao);
        assertThat(userService.findByUserName("Lita"),is(user));
        assertThat(userService.findByUserName(""),is(nullValue()));

    }
    @Test
    public void create(){
        Role role = new Role();
        role.setRoleName("admin");
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        User user = mock(User.class);
        when(user.getName()).thenReturn("userName");
        when(user.getEmail()).thenReturn("userEmail");
        when(user.getId()).thenReturn(1L);
        when(user.getPassword()).thenReturn("userPassword");
        when(user.getRoles()).thenReturn(roles);
        UserDao dao = mock(UserDao.class);
        when(dao.create(user)).thenReturn(user);
        RoleDao roleDao = mock(RoleDao.class);

        when(roleDao.findByRoleName("admin")).thenReturn(role);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(dao);
        userService.setRoleDao(roleDao);

        assertThat(userService.create(user),is(user));
        assertThat(userService.create(user).getId(),is(1L));
        assertThat(userService.create(user).getEmail(),is("userEmail"));
        assertThat(userService.create(user).getName(),is("userName"));
        assertThat(userService.create(user).getPassword(),is("userPassword"));

    }

}
