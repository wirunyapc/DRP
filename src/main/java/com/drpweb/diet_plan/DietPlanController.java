package com.drpweb.diet_plan;

import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.disease.DiseaseService;
import com.drpweb.food.Food;
import com.drpweb.food.FoodDao;
import com.drpweb.food.FoodService;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 8/22/2016.
 */
@CrossOrigin
@RestController
public class DietPlanController {
    @Autowired
    FoodService foodService;
    @Autowired
    DiseaseService diseaseService;
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



    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getFoodPlan",method = RequestMethod.GET)
    public String getFoodPlan(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        System.out.println("user for food plan : "+user.getUsername());
        DietPlan dietPlan = dietPlanDao.findByUserId(user.getId());
        List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId());

        String result = "[";
        System.out.println("====================================================");


            String meal = "[";


            int count =1;

            for (DailyMeal daily : dailyMeals) {
                String data = "[";

                data += "\"" + daily.getDate().toString() + "\",";
                data += "\"" + daily.getMealId().toString() + "\",";
                Food food = foodService.findOne(daily.getFoodId());
                data += "\"" + food.getFoodName() + "\"";

                if(count==dailyMeals.size()){
                    data += "]";
                }else{
                    data += "],";
                    count++;
                }

                meal += data;
            }

                meal += "]";

            result += meal;

        result += "]";

        return result;


    }



//    public Solver getPatient() throws SQLException {
//
//        Food food;
//        Disease disease;
//        int amount = 7;
//        int period = 3;
//
//
//        disease = diseaseService.getDiseaseById(4);
//        DailyDiet dailyDiet = new DailyDiet();
//        Solver s;
//        food = foodService.getFood();
//
//
//
//
//        s = dailyDiet.solvePatient(amount, period,
//                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
//                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(),  disease,1232);
//        List<IntegerVariable[][]> varList = dailyDiet.getVars();
//
//        String result = dailyDiet.toJson();
//        savePlan(result,user);
//        System.out.println("Resultttttttttttttttttttttt");
//        System.out.println(result);
//
//
//
//        return s;
//    }
@RequestMapping(value = "/getDisease",method = RequestMethod.GET)
public List<Disease> getDisease() throws SQLException {

    List<Disease> diseases = diseaseDao.findAll();



    String str = "{";
    for (Disease disease: diseases) {

                str+= "\""+disease.getDiseaseName()+"\",";

    }
    str+="}";

    System.out.print("str "+str);
    return  diseases;
}

    @RequestMapping(value = "/getFoods",method = RequestMethod.GET)
    public List<Food> getFoods() throws SQLException {

        List<Food> foods = foodDao.findAll();
        return  foods;
    }

    @RequestMapping(value = "/setDisease",method = RequestMethod.GET)
    public String setDisease(@RequestParam("disease")String diseaseName,@RequestParam("name")String username) throws SQLException {
        Disease disease = diseaseService.findByDiseaseName(diseaseName);
        System.out.println("user disease" +disease.getDiseaseName());
        User user = userService.findByUserName(username);
        user.setDiseaseId(disease.getId());
        userDao.update(user);
        System.out.println("disease updated!" + user.getId());
        //userService.setCurrentUser(user.getUsername());
        String result = dietPlanService.createPatientPlan(user.getUsername());
        return result;


    }


    @RequestMapping(value = "/bmi",method = RequestMethod.GET)
    public String getBmi(@RequestParam("name")String username) throws SQLException {
        // String user = String.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        User user = userService.findByUserName(username);
        DailyDiet dd = new DailyDiet();
        String bmiValue = dd.calBMI(user.getWeight(), user.getHeight());
        return bmiValue;

    }

//"["+ "\"" +bmiValue+ "\"" + "]";
}
