package com.drpweb.diet_plan;

import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.daily_meal.DailyMealService;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.food.Food;
import com.drpweb.food.FoodDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.role.Role;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.setmenu.SetMenuDao;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ADMIN on 8/22/2016.
 */
@CrossOrigin
@RestController
public class DietPlanController {

    @Autowired
    DiseaseDao diseaseDao;
    @Autowired
    UserService userService;
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    DailyMealDao dailyMealDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DietPlanService dietPlanService;
    @Autowired
    FoodDao foodDao;
    @Autowired
    DailyMealService dailyMealService;
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getFoodPlan",method = RequestMethod.GET)
    public String getFoodPlan(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        System.out.println("user fobbbbbbbr food plan : "+user.getUsername());
        DietPlan dietPlan = dietPlanDao.findByUserId(user.getId());
        List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());


        if(dailyMeals.size()==0) {
            System.out.println("member diet plan nullll");

                Set<Role> roles = user.getRoles();
                for(Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
                    Role role = it.next();
                    if (role.getRoleName().equals("member")){
                       dietPlanService.createPlan(user.getUsername());
                        dailyMeals=dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());
                        System.out.print("I have a plan");
                    }else{
                        return null;
                    }


                }

        }


            System.out.println("====================================================");
        //String result = "[";

            String day = "[";

            int foodCount = 1;
            int count = 1;
            int daycount=1;

            for (DailyMeal daily : dailyMeals) {

                    String meal = "[";

                    SetMenu setMenu = setMenuDao.findOne(daily.getSetMenu_id());

                    List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(setMenu.getSetMenu_id());
                    for (FoodSetMenu f : foodSetMenus) {
                        String fooddata = "[";
                        System.out.println("foodidx count" + f.getFoodIndex());
                        fooddata += "\"" + daily.getDate().toString() + "\",";
                        fooddata += "\"" + daily.getMealId().toString() + "\",";
                        fooddata += "\"" + f.getFoodIndex() + "\",";
                        fooddata += "\"" + foodDao.findOne(f.getFoodId()).getFoodName_eng() + "\"";
                        if (foodCount == foodSetMenus.size()) {
                            fooddata += "]";
                            foodCount = 1;
                        } else {
                            fooddata += "],";
                            foodCount++;
                        }

                        meal += fooddata;
                    }


                    if (count == dailyMeals.size()) {
                        meal += "]";
                    } else {
                        meal += "],";
                        count++;
                    }

                    day += meal;
           }
            day += "]";
//            result += day;
//
//            result += "]";


            return day;


    }

    @RequestMapping(value = "/getDiseases",method = RequestMethod.GET)
    public List<Disease> getDisease() throws SQLException {

    return  diseaseDao.findAll();
}

    @RequestMapping(value = "/getCurrentDisease",method = RequestMethod.GET)
    public String getCurrentDisease(@RequestParam("name")String username) throws SQLException {

        User user = userService.findByUserName(username);
        Disease disease = diseaseDao.findOne(user.getDiseaseId());

        return  "["+"\""+disease.getDiseaseName()+"\""+"]";
    }

    @RequestMapping(value = "/getFoods",method = RequestMethod.GET)
    public List<Food> getFoods() throws SQLException {

        return  foodDao.findAll();
    }

    @RequestMapping(value = "/getBmr",method = RequestMethod.GET)
    public String getBMR(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        int bmr = dietPlanService.getBmr(user.getWeight(),user.getHeight(),dietPlanService.getAge(user.getDob()),user.getGender());
        return  "["+"\""+bmr+"\""+"]";
    }

    @RequestMapping(value = "/setFood",method = RequestMethod.GET)
    public Boolean setFood(@RequestParam("food")String foodName,@RequestParam("name")String username,@RequestParam("meal")Long meal,@RequestParam("date")String date,@RequestParam("idx")int foodIndex) throws SQLException {
        Date myDate = dietPlanService.parseDate(date);
        System.out.println("Date"+myDate);
        DietPlan dietPlan = dietPlanDao.findByUserId(userService.findByUserName(username).getId());
        System.out.println("Diet plan Id"+dietPlan.getDietPlanId());
        List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());
        Food food = foodDao.findByFoodName(foodName);
        System.out.println("food to set"+food.getFoodId());

        for (DailyMeal daily : dailyMeals) {

            if(daily.getDate().toString().equals(date+" 00:00:00.0")){
                if(daily.getMealId().equals(meal)) {
                    SetMenu setMenu = setMenuDao.findOne(daily.getSetMenu_id());
                    List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(setMenu.getSetMenu_id());
                    foodSetMenus.stream().filter(f -> f.getFoodIndex() == foodIndex).forEach(f -> {
                        f.setFoodId(food.getFoodId());
                        foodSetMenuDao.update(f);
                    });

                }
            }
            else {
                return false;
            }
        }
        return true;
    }


    @RequestMapping(value = "/setDisease",method = RequestMethod.GET)
    public String setDisease(@RequestParam("disease")String diseaseName,@RequestParam("name")String username) throws SQLException {
        Disease disease = diseaseDao.findByDiseaseName(diseaseName);
        System.out.println("user disease" +disease.getDiseaseName());
        User user = userService.findByUserName(username);
        user.setDiseaseId(disease.getId());
        userDao.update(user);
        System.out.println("disease updated!" + user.getDiseaseId());
        DietPlan dietPlan = dietPlanService.findByUserId(user.getId());

        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setStartDate(startDate);

        LocalDate end = today.plusDays(user.getDuration());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setEndDate(endDate);
        dietPlanDao.update(dietPlan);

        dailyMealService.delete(dietPlan.getDietPlanId());
        return dietPlanService.createPatientPlan(user.getUsername());


    }

    @RequestMapping(value = "/bmi",method = RequestMethod.GET)
    public String getBmi(@RequestParam("name")String username) throws SQLException {
        // String user = String.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        User user = userService.findByUserName(username);
        DailyDiet dd = new DailyDiet();
        return dd.calBMI(user.getWeight(), user.getHeight());

    }

//"["+ "\"" +bmiValue+ "\"" + "]";
}
