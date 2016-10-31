package com.drpweb.diet_plan;

import choco.kernel.solver.Solver;
import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.role.Role;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.setmenu.SetMenuService;
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
import java.util.*;

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
    DailyMealDao dailyMealDao;
   @Autowired
    SetMenuService setMenuService;
    @Autowired
    DiseaseDao diseaseDao;


    @Override
    public DietPlan findByUserId(Long id) {
        return dietPlanDao.findByUserId(id);
    }
    @Override
    public void createPlan(String name) throws SQLException {
      User user = userService.findByUserName(name);
        SetMenu setMenu;
        int amount = user.getDuration();
        System.out.println("duration"+amount);
        int period = 3;
        int bmr = getBmr(user.getWeight(),user.getHeight(),getAge(user.getDob()),user.getGender());
        System.out.print("bmr in create plan : "+bmr);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;

        List<SetMenu> setMenus = setMenuService.getSetMenu();
        setMenu = setMenuService.toSetMenu(setMenus);
        System.out.println("setMenu" + setMenu);

        Set<Role> roles = user.getRoles();
        for(Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("member")){
                s = dailyDiet.solve(amount, period,
                        setMenu.getArr_setMenu_id(), setMenu.getArr_total_cal(),setMenu.getArr_total_fat(), setMenu.getArr_total_carboh(), setMenu.getArr_total_protein(),
                        setMenu.getTotal_cals(), setMenu.getTotal_fats(), setMenu.getTotal_carbohs(), setMenu.getTotal_proteins(),0,0,0,0, bmr,"member");
            }


        }

        String result = dailyDiet.toJson();
        savePlan(result,user);
        System.out.println("Resultttttttttttttttttttttt"+result);

    }

    @Override
    public String createPatientPlan(String name) throws SQLException {
        User user = userService.findByUserName(name);
        System.out.println("User for plan" + user.getUsername());
        SetMenu setMenu;
        int amount = user.getDuration();
        System.out.println("duration"+amount);
        int period = 3;
        int bmr = getBmr(user.getWeight(),user.getHeight(),getAge(user.getDob()),user.getGender());
        System.out.print("bmr in create plan : "+bmr);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;

        Disease userDisease = diseaseDao.findOne(user.getDiseaseId());
        List<SetMenu> setMenus = setMenuService.getSetMenuByDisease(userDisease.getId());
        setMenu = setMenuService.toSetMenu(setMenus);
        System.out.println("Set to Eat" + setMenu);
        System.out.println("disease for plan :"+userDisease.getDiseaseName());

        Set<Role> roles = user.getRoles();
        for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("patient")) {

                s = dailyDiet.solve(amount, period,
                        setMenu.getArr_setMenu_id(), setMenu.getArr_total_cal(), setMenu.getArr_total_fat(), setMenu.getArr_total_carboh(), setMenu.getArr_total_protein(),
                        setMenu.getTotal_cals(), setMenu.getTotal_fats(), setMenu.getTotal_carbohs(), setMenu.getTotal_proteins(), (int)userDisease.getKcal(),(int)userDisease.getFat(),(int)userDisease.getProt(),(int)userDisease.getCarboh(), bmr,"patient");
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

        int[] setMenuIds = new int[3];
        int setMenuNum = 0;

        for (int i = 0; i < splitById.length; i++) {
            int indexOfColon = 0;
            int indexOfComma = 0;
            String meal = splitById[i];
            String foodId = "";


            indexOfColon = meal.indexOf(":");
            indexOfComma = meal.indexOf(",");

            if (indexOfColon != -1 && indexOfComma != -1) {
                foodId = meal.substring(indexOfColon + 1, indexOfComma - 1);
                setMenuIds[setMenuNum] = Integer.parseInt(foodId);


                System.out.println(meal);
                System.out.println("setNum");
                System.out.println(setMenuIds[setMenuNum] + " : " + setMenuNum);


                if (setMenuNum == 2) {

                    System.out.println("add plan");
                    dailyMeals.add(setMenuIds);

                    setMenuIds = new int[3];
                    setMenuNum = 0;

                } else {

                    setMenuNum = setMenuNum + 1;
                    System.out.println("Increase setNum : " + setMenuNum);
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
                dailyMeal.setSetMenu_id(daily[i]);
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
