package com.drpweb.user;

import com.drpweb.diet_plan.DietPlanController;
import com.drpweb.transfer.TokenTransfer;
import com.drpweb.transfer.UserTransfer;
import com.drpweb.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 7/8/2559.
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserAuthenticationController {
    @Autowired
    UserDetailsService userDetailsService;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    DietPlanController dietPlanController;

    @Autowired
    UserService userService;

    public UserAuthenticationController() {
    }

    public UserAuthenticationController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */

    @RequestMapping(method = RequestMethod.GET)
    public UserTransfer getUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails usersDetails = (UserDetails) principal;
        userService.setCurrentUser(usersDetails.getUsername());
        return new UserTransfer(usersDetails.getUsername(),this.createRoleMap(usersDetails));
    }

    private Map<String, Boolean> createRoleMap(UserDetails userDetails)
    {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }

    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public TokenTransfer authenticate(@RequestBody String body){
        // The body has been sent by username=a&password=b format
        String[] token = body.split("&");
        String username = token[0].split("=")[1];
        String password = token[1].split("=")[1];
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication =
                this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
                /*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
        UserDetails userDetails =
                this.userDetailsService.loadUserByUsername(username);
        TokenTransfer t = new TokenTransfer(TokenUtils.createToken(userDetails));

        return t;
    }


}
