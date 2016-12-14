package com.drpweb.diet_plan;

import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.daily_meal.DailyMealService;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.food.FoodDao;
import com.drpweb.food_set_disease.FoodSetDiseaseDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.role.Role;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.setmenu.SetMenuDao;
import com.drpweb.setmenu.SetMenuService;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    SetMenuService setMenuService;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    FoodSetDiseaseDao foodSetDiseaseDao;


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getFoodPlan",method = RequestMethod.GET)
    public String getFoodPlan(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        DietPlan dietPlan = dietPlanDao.findByUserId(user.getId());
        List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());

        if(dailyMeals.size()==0) {

                Set<Role> roles = user.getRoles();
                for(Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
                    Role role = it.next();
                    if (role.getRoleName().equals("member")){
                        System.out.println("Diet plan nullll");
                        LocalDate today = LocalDate.now();
                        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        dietPlan.setStartDate(startDate);

                        LocalDate end = today.plusDays(user.getDuration()-1);
                        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        dietPlan.setEndDate(endDate);

                        dietPlanDao.update(dietPlan);
                        System.out.println("Plan set date" + dietPlan.getStartDate());

                       dietPlanService.createPlan(user.getUsername());
                        dailyMeals=dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());

//                       if(dailyMeals.isEmpty()){
//                           return "["+"\""+"error"+"\""+"]";
//                       }
                    }else{
                        return "["+"\""+"null"+"\""+"]";
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

                    List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(setMenu.getSetmenu());
                    for (FoodSetMenu f : foodSetMenus) {
                        String fooddata = "[";
                        System.out.println("foodidx count" + f.getFoodIndex());
                        fooddata += "\"" + daily.getDate().toString() + "\",";
                        fooddata += "\"" + daily.getMealId().toString() + "\",";
                        fooddata += "\"" + f.getFoodIndex() + "\",";
                        fooddata += "\"" + foodDao.findOne(f.getFoodId()).getFoodName_eng() + "\",";
                        fooddata += "\"" + setMenu.getTotal_cal() + "\"";
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
    public List<List<String>> getFoods(@RequestParam("name")String username) throws SQLException {
        List<SetMenu> setMenus = setMenuService.getSetMenu(userService.findByUserName(username));
        return setToJSON(setMenus);
    }


    public List<List<String>> setToJSON(List<SetMenu> setMenus) throws SQLException {
        List<List<String>> setName = new ArrayList<>();

        List<FoodSetMenu> foodSetMenu;

        for (SetMenu s: setMenus) {
            foodSetMenu = foodSetMenuDao.findBySetmenu(s.getSetmenu());
            String foodName = "";
            List<String> value = new ArrayList<>();
            value.add(""+s.getSetmenu());
            value.add(""+s.getTotal_cal());
            for (FoodSetMenu foodSet: foodSetMenu) {
                foodName += (foodDao.findOne(foodSet.getFoodId()).getFoodName_eng() + "|");

            }
            value.add(foodName);
            setName.add(value);
        }

        return setName;
    }

    @RequestMapping(value = "/getFoodsByDisease",method = RequestMethod.GET)
    public List<List<String>> getFoodsByDisease(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        List<SetMenu> setMenus = setMenuService.getSetMenuByDisease(user);

        return  setToJSON(setMenus);
    }
    @RequestMapping(value = "/getAllSetMenu",method = RequestMethod.GET)
    public List<List<String>> getAllSetMenu() throws SQLException {
        List<SetMenu> setMenus = setMenuDao.findAll();

        return  setToJSON(setMenus);
    }

    @RequestMapping(value = "/getBmr",method = RequestMethod.GET)
    public String getBMR(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        int bmr = dietPlanService.getBmr(user.getWeight(),user.getHeight(),dietPlanService.getAge(user.getDob()),user.getGender());
        return  "["+"\""+bmr+"\""+"]";
    }

    @RequestMapping(value = "/setFood",method = RequestMethod.GET)
    public Boolean setFood(@RequestParam("set")String set,@RequestParam("name")String username,@RequestParam("meal")Long meal,@RequestParam("date")String date) throws SQLException {
        Date myDate = dietPlanService.parseDate(date);
        DietPlan dietPlan = dietPlanDao.findByUserId(userService.findByUserName(username).getId());
        System.out.println("User diet plan Id"+dietPlan.getDietPlanId());
        List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());

        int setId = Integer.parseInt(set);
        System.out.println("Set menu ID to update :"+ setId);

        for (DailyMeal daily : dailyMeals) {

            if(daily.getDate().toString().equals(date+" 00:00:00.0")){
                if(daily.getMealId().equals(meal)) {
                    SetMenu setMenu = setMenuDao.findOne(setId);
                    System.out.println("Into the meal to update set" + daily.getMealId());
                   /* List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(setMenu.getSetmenu());
                    foodSetMenus.stream().filter(f -> f.getFoodIndex() == foodIndex).forEach(f -> {
                        f.setFoodId(food.getFoodId());*/
                        daily.setSetMenu_id(setMenu.getSetmenu());
                        dailyMealDao.update(daily);
                  //  });

                }
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

        LocalDate end = today.plusDays(user.getDuration()-1);
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
