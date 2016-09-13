package com.drpweb.user;

import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.diet_plan.DietPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    DietPlanService dietPlanService;

    @RequestMapping(value = "/user/all",method = RequestMethod.GET)
    public List<User> list(){
        return userService.findAll();
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public User create(@RequestBody User user){
        User u = userService.create(user);
        DietPlan dietPlan = new DietPlan();

        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setStartDate(startDate);

        LocalDate end = today.plusDays(user.getDuration());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setEndDate(endDate);

        dietPlan.setUserId(u.getId());
        dietPlanDao.create(dietPlan);

        try {
            dietPlanService.createPlan(user.getUsername());
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
