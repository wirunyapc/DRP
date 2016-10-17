package com.drpweb.food;

import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.setmenu.SetMenuDao;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 10/13/2016.
 */
@CrossOrigin
@RestController
public class FoodController {
    @Autowired
    FoodDao foodDao;
    @Autowired
    DietPlanService dietPlanService;
    @Autowired
    DailyMealDao dailyMealdao;
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    UserDao userDao;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getTotalDietCal",method = RequestMethod.GET)
    public int calculateDiet(@RequestParam("date")String date,@RequestParam("name")String name) throws SQLException {
        /*  int totalDietCal = 0;
        Food foodBreakfast = foodDao.findByFoodName(bfast);
        Food foodLunch = foodDao.findByFoodName(lunch);
        Food foodDinner = foodDao.findByFoodName(dinner);
        totalDietCal = (foodBreakfast.getKal()+foodLunch.getKal())+foodDinner.getKal();
        System.out.println("totalDietcal" + totalDietCal);
        return  totalDietCal;*/

        int totalDietCal = 0;

        User user = userDao.findByUsername(name);
        DietPlan userDietPlan = dietPlanService.findByUserId(user.getId());
        List<DailyMeal> dailymeals = dailyMealdao.findByDietPlanId(userDietPlan.getDietPlanId());
        for (DailyMeal daily : dailymeals) {
            if(daily.getDate().toString().equals(date+" 00:00:00.0")){
                List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(daily.getSetMenu_id());
                for (FoodSetMenu f: foodSetMenus) {
                    totalDietCal+=foodDao.findOne(f.getFoodId()).getKal();
                }
            }
        }

        System.out.println("Bmr" + totalDietCal);
        return  totalDietCal;

    }
}
