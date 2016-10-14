package com.drpweb.diet_plan;

import choco.kernel.solver.Solver;
import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.food.Food;
import com.drpweb.food.FoodService;
import com.drpweb.role.Role;
import com.drpweb.user.User;
import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ADMIN on 9/12/2016.
 */
@Service
public class DietPlanServiceImpl implements DietPlanService{
    @Autowired
    DietPlanDao dietPlanDao;

    @Autowired
    UserService userService;

    @Autowired
    FoodService foodService;

    @Autowired
    DailyMealDao dailyMealDao;


    @Autowired
    DiseaseDao diseaseDao;


    @Override
    public DietPlan findByUserId(Long id) {
        return dietPlanDao.findByUserId(id);
    }
    @Override
    public void createPlan(String name) throws SQLException {
        User user = userService.findByUserName(name);
        Food food;
        int amount = user.getDuration();
        System.out.println("duration"+amount);
        int period = 3;
        int bmr = getBmr(user.getWeight(),user.getHeight(),getAge(user.getDob()),user.getGender());
        System.out.print("bmr in create plan : "+bmr);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;

        food = foodService.getFood();

        Set<Role> roles = user.getRoles();
        for(Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("member")){
                s = dailyDiet.solve(amount, period,
                        food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                        food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(),0,0,0,0, bmr,"member");
            }


        }

        String result = dailyDiet.toJson();
        savePlan(result,user);
        System.out.println("Resultttttttttttttttttttttt");

    }

    @Override
    public String createPatientPlan(String name) throws SQLException {
        User user = userService.findByUserName(name);
        System.out.println("User for plan" + user.getUsername());
        Food food;
        int amount = user.getDuration();
        System.out.println("duration"+amount);
        int period = 3;
        int bmr = getBmr(user.getWeight(),user.getHeight(),getAge(user.getDob()),user.getGender());
        System.out.print("bmr in create plan : "+bmr);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();
        Disease userDisease = diseaseDao.findOne(user.getDiseaseId());
        System.out.println("disease for plan :"+userDisease.getDiseaseName());

        Set<Role> roles = user.getRoles();
        for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("patient")) {

                s = dailyDiet.solve(amount, period,
                        food.getArr_id(), food.getArr_kal(), food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                        food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(), (int)userDisease.getKcal(),(int)userDisease.getFat(),(int)userDisease.getProt(),(int)userDisease.getCarboh(), bmr,"patient");
            }
        }

        String result = dailyDiet.toJson();
        savePlan(result,user);
        System.out.println("Resultttttttttttttttttttttt");
        System.out.println(result);
        return result;
    }

    @Override
    public Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public void savePlan(String result,User user) {
        DailyMeal dailyMeal;

        //      System.out.println("userId in save" + user.getId());
        DietPlan dietPlan = dietPlanDao.findByUserId(user.getId());
//        System.out.println("dietplan user Id"+dietPlan.getUserId());
//        System.out.println("dietplan Id"+dietPlan.getDietPlanId());

        Date startDate = dietPlan.getStartDate();
        //       String str = result;
//        System.out.print("Return Value :" );
//        System.out.println(Str.substring(11) );
        // String mock = "[[['id_0_0:25','kal_0_0:280','fat_0_0:16','pro_0_0:28','carbo_0_0:50'],['id_0_1:96','kal_0_1:650','fat_0_1:10','pro_0_1:20','carbo_0_1:70'],['id_0_2:37','kal_0_2:390','fat_0_2:3','pro_0_2:12','carbo_0_2:44']],[['id_1_0:26','kal_1_0:380','fat_1_0:18','pro_1_0:20','carbo_1_0:45'],['id_1_1:136','kal_1_1:470','fat_1_1:2','pro_1_1:9','carbo_1_1:18'],['id_1_2:27','kal_1_2:400','fat_1_2:10','pro_1_2:30','carbo_1_2:90']]]";
        //  System.out.println("Hello World");
        String[] splitById = result.split("id");
        ArrayList<int[]> dailyMeals = new ArrayList<int[]>();

        int[] foodIds = new int[3];
        int foodNum = 0;

        for (int i = 0; i < splitById.length; i++) {
            int indexOfColon = 0;
            int indexOfComma = 0;
            String meal = splitById[i];
            String foodId = "";


            indexOfColon = meal.indexOf(":");
            indexOfComma = meal.indexOf(",");

            if (indexOfColon != -1 && indexOfComma != -1) {
                foodId = meal.substring(indexOfColon + 1, indexOfComma - 1);
                foodIds[foodNum] = Integer.parseInt(foodId);


                System.out.println(meal);
                System.out.println("foodNum");
                System.out.println(foodIds[foodNum] + " : " + foodNum);


                if (foodNum == 2) {

                    System.out.println("add plan");
                    dailyMeals.add(foodIds);

                    foodIds = new int[3];
                    foodNum = 0;

                } else {

                    foodNum = foodNum + 1;
                    System.out.println("Increase foodNum : " + foodNum);
                }


            }


        }

        /*Add to database*/
        int dayCount = 0;
        for (int[] daily : dailyMeals) {
            System.out.print("duration : " + dailyMeals.size());


            for (int i = 0; i < daily.length; i++) {
                dailyMeal = new DailyMeal();
//                         Calendar cal = Calendar.getInstance();
//                         cal.setTime(startDate);
//                         cal.add(Calendar.DATE, dayCount); //minus number would decrement the days
//                         Date dailyDate = cal.getTime();
                LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dateCount = start.plusDays(dayCount);
                Date dailyDate = Date.from(dateCount.atStartOfDay(ZoneId.systemDefault()).toInstant());
                System.out.print(dailyDate);
                dailyMeal.setDate(dailyDate);

                dailyMeal.setDietPlanId(dietPlan.getDietPlanId());
                System.out.println("Long id = " + (long) daily[i]);
                dailyMeal.setFoodId(daily[i]);
                dailyMeal.setMealId((long) i + 1);

                dailyMealDao.create(dailyMeal);

            }
            dayCount = dayCount + 1;

        }
    }

        @Override
        public int getAge(Date bd){

           // Date bd = user.getDob();
            LocalDate today = LocalDate.now();
            LocalDate birthday = bd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Period p = Period.between(birthday,today);

            return p.getYears();
        }

    @Override
    public int getBmr(double weight, double height, int age, String gender) throws SQLException {
        double bmr;

        if(gender.equals("female")) {
            bmr = (10 * weight) + (6.25 * height) - (5 * (double)age) - 161;
        }else{
            bmr = (10 * weight) + (6.25 * height) - (5 * (double)age) +5;
        }
        System.out.print("bmr :"+bmr);

        return (int) bmr;
    }



}
