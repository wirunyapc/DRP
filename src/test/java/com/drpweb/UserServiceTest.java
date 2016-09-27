package com.drpweb;

import com.drpweb.role.Role;
import com.drpweb.role.RoleDao;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import com.drpweb.user.UserServiceImpl;
import org.junit.Test;

import java.util.Date;
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
        Date dob = new Date("04/10/1995");
        Role role = new Role();
        role.setRoleName("member");

        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("Wirunya");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(7);
        when(user.getDiseaseId()).thenReturn(null);

        UserDao dao = mock(UserDao.class);
        when(dao.create(user)).thenReturn(user);
        RoleDao roleDao = mock(RoleDao.class);

        when(roleDao.findByRoleName("member")).thenReturn(role);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(dao);
        userService.setRoleDao(roleDao);
        when(user.getRoles()).thenReturn(roles);


        when(dao.findByUsername("Wirunya")).thenReturn(user);
        when(dao.findByUsername("")).thenReturn(null);


        assertThat(userService.findByUserName("Wirunya"),is(user));
        assertThat(userService.create(user).getId(),is(1L));
        assertThat(userService.create(user).getUsername(),is("Wirunya"));
        assertThat(userService.create(user).getPassword(),is("123456"));
        assertThat(userService.create(user).getEmail(),is("bo_zooza@hotmail.com"));
        assertThat(userService.create(user).getName(),is("Wirunya"));
        assertThat(userService.create(user).getLastName(),is("Pajcha"));
        assertThat(userService.create(user).getGender(),is("female"));
        assertThat(userService.create(user).getDob(),is(dob));
        assertThat(userService.create(user).getWeight(),is(48.0));
        assertThat(userService.create(user).getHeight(),is(163.0));
        assertThat(userService.create(user).getDuration(),is(7));
        assertThat(userService.create(user).getRoles(),is(roles));
        assertThat(userService.create(user).getDiseaseId(),is(nullValue()));
        assertThat(userService.findByUserName(""),is(nullValue()));

    }
    @Test
    public void create(){
        Role role = new Role();
        role.setRoleName("member");

        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        User user = mock(User.class);
        Date dob = new Date("04/10/1995");
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("Wirunya");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(7);
        when(user.getDiseaseId()).thenReturn(null);

        UserDao dao = mock(UserDao.class);
        when(dao.create(user)).thenReturn(user);
        RoleDao roleDao = mock(RoleDao.class);

        when(roleDao.findByRoleName("member")).thenReturn(role);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(dao);
        userService.setRoleDao(roleDao);
        when(user.getRoles()).thenReturn(roles);

        assertThat(userService.create(user),is(user));
        assertThat(userService.create(user).getId(),is(1L));
        assertThat(userService.create(user).getUsername(),is("Wirunya"));
        assertThat(userService.create(user).getPassword(),is("123456"));
        assertThat(userService.create(user).getEmail(),is("bo_zooza@hotmail.com"));
        assertThat(userService.create(user).getName(),is("Wirunya"));
        assertThat(userService.create(user).getLastName(),is("Pajcha"));
        assertThat(userService.create(user).getGender(),is("female"));
        assertThat(userService.create(user).getDob(),is(dob));
        assertThat(userService.create(user).getWeight(),is(48.0));
        assertThat(userService.create(user).getHeight(),is(163.0));
        assertThat(userService.create(user).getDuration(),is(7));
        assertThat(userService.create(user).getRoles(),is(roles));
        assertThat(userService.create(user).getDiseaseId(),is(nullValue()));


    }

}
