package com.drpweb.user;

import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Asus on 7/8/2559.
 */
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    DietPlanService dietPlanService;

    @RequestMapping(value = "/updateUser",method = RequestMethod.GET)
    public String updateUserProfile(@RequestParam("currentUser")String current,@RequestParam("weight")double weight,@RequestParam("height")double height,@RequestParam("duration")int duration){
        User user = userDao.findByUsername(current);
        user.setWeight(weight);
        user.setHeight(height);
        user.setDuration(duration);
        userDao.update(user);



        return "["+"\""+"success"+"\""+"]";
    }


    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User create(@RequestBody User user){

        User u = userService.create(user);
        DietPlan dietPlan = new DietPlan();

        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setStartDate(startDate);

        LocalDate end = today.plusDays(user.getDuration()-1);
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setEndDate(endDate);

        dietPlan.setUserId(u.getId());
        dietPlanDao.create(dietPlan);

        try {
            Set<Role> roles = u.getRoles();
            for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
                Role role = it.next();
                if (role.getRoleName().equals("member")){
                    dietPlanService.createPlan(user.getUsername());

                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return u;
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public  User getByUsername(@RequestParam("name")String name){
        return userService.findByUserName(name);
    }
}
