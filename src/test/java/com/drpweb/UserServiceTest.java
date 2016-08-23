package com.drpweb;

import com.drpweb.user.*;
import org.junit.Test;
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

        UserService userService = new UserServiceImpl(dao);

        assertThat(userService.findByUserName("Lita"),is(user));
        assertThat(userService.findByUserName(""),is(nullValue()));

    }
    @Test
    public void create(){
        User user = mock(User.class);
        when(user.getName()).thenReturn("username");
        when(user.getEmail()).thenReturn("userEmail");
        when(user.getId()).thenReturn(1L);
        when(user.getPassword()).thenReturn("userPassword");
        UserDao dao = mock(UserDao.class);
        when(dao.create(user)).thenReturn(user);

        UserService userService = new UserServiceImpl(dao);

        assertThat(userService.create(user),is(user));
        assertThat(userService.create(user).getId(),is(1L));
        assertThat(userService.create(user).getEmail(),is("userEmail"));
        assertThat(userService.create(user).getName(),is("username"));
        assertThat(userService.create(user).getPassword(),is("userPassword"));

    }

}
