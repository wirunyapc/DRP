package com.drpweb.diet_plan;

import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseService;
import com.drpweb.food.Food;
import com.drpweb.food.FoodService;
import com.drpweb.user.User;
import com.drpweb.user.UserService;
import com.drpweb.util.DailyDiet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
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
    UserService userService;


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/plan",method = RequestMethod.GET)
   /* public Solver getPlan(@RequestParam("userName")String userName) throws SQLException {
        User user = userService.findByUserName(userName);
        Food food;

        int amount = user.getDuration();
        int period = 3;
        int bmr = getBmr(user);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();

        s = dailyDiet.solve(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(), bmr);
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
       // food = ds.getFood();

        ObjectMapper mapper = new ObjectMapper();
        List plan = dailyDiet.plan;
//        final String stringify = mapper.writeValueAsString(dailyDiet.plan.toString());
        return s;
    }*/
    //For test!
   public String getPlan() throws SQLException {

        Food food;

        int amount = 7;
        int period = 3;

        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();

        s = dailyDiet.solve(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(), 2000);
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
        // food = ds.getFood();

        ObjectMapper mapper = new ObjectMapper();
        List plan = dailyDiet.plan;
//        final String stringify = mapper.writeValueAsString(dailyDiet.plan.toString());
        String result = dailyDiet.toJson();
        System.out.println(result);
        return result;
    }
    public void writeListToJsonArray() throws IOException {
        final List<Event> list = new ArrayList<Event>(2);

//        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

//        mapper.writeValue(out, list);

//        final byte[] data = out.toByteArray();
        System.out.println(mapper.writeValueAsString(list));
    }

    @RequestMapping(value = "/patient",method = RequestMethod.GET)
   /* public Solver getPatient(@RequestParam("userName")String userName) throws SQLException {
        User user = userService.findByUserName(userName);
        Food food;
        Disease disease;
        int amount = user.getDuration();
        int period = 3;

        int diseaseId = (int) user.getDisease().getId();
        disease = diseaseService.getDiseaseById(diseaseId);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();


        int bmr = getBmr(user);

        s = dailyDiet.solvePatient(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(),  disease,bmr);
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
        // food = ds.getFood();

        ObjectMapper mapper = new ObjectMapper();
        List plan = dailyDiet.plan;
//        final String stringify = mapper.writeValueAsString(dailyDiet.plan.toString());
        return s;
    }*/
    //For test!
    public Solver getPatient() throws SQLException {

        Food food;
        Disease disease;
        int amount = 7;
        int period = 3;


        disease = diseaseService.getDiseaseById(4);
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();




        s = dailyDiet.solvePatient(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins(),  disease,1232);
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
        // food = ds.getFood();

        ObjectMapper mapper = new ObjectMapper();
        List plan = dailyDiet.plan;
//        final String stringify = mapper.writeValueAsString(dailyDiet.plan.toString());
        return s;
    }

   public int getBmr(User user) throws SQLException {
        double bmr;
        Date bd = user.getDob();
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(bd.getYear(),bd.getMonth(),bd.getDay());

        Period p = Period.between(birthday,today);
        double age = p.getYears();
        System.out.print(p.getYears());
        if(user.getGender()=="female") {
             bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * age) - 161;
        }else{
             bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * age) +5;
        }
        System.out.print("bmr :"+bmr);

        return (int) bmr;
    }

    //For test!
   /* public int getBmr(@RequestParam("weight")double weight, @RequestParam("height")double height, @RequestParam("age")int age) throws SQLException {

        double bmr = (10 * weight) + (6.25 * height) - (5 * age)-161;
        System.out.print("bmr :"+bmr);

        return (int) bmr;
    }*/
    @RequestMapping(value = "/bmi",method = RequestMethod.GET)
    public String getBmi(@RequestParam("weight")double weight, @RequestParam("height")double height) throws SQLException {
        DailyDiet dd = new DailyDiet();
        String result = dd.calBMI(weight, height);
        System.out.print("result :"+result);

        return result;
    }


}
