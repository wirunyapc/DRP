package com.drpweb;

import com.drpweb.security.SecurityUser;
import com.drpweb.user.UserAuthenticationController;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.*;

/**
 * Created by Asus on 10/8/2559.
 */
public class UserAuthenticationControllerTest {

    @Test
    public void authenticate() {
        SecurityUser user = mock(SecurityUser.class);
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn("admin");
        when(user.getEmail()).thenReturn("admin@admin.com");
        when(user.getPassword()).thenReturn("1234");
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(userDetailsService.loadUserByUsername("admin")).thenReturn(user);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        UserAuthenticationController userAuthenticationController = new UserAuthenticationController(userDetailsService,authenticationManager);
        userAuthenticationController.authenticate("username=admin&passworld=1234");

        verify(userDetailsService).loadUserByUsername("admin");

    }
}
