package com.drpweb.diet_plan;

import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import com.drpweb.food.Food;
import com.drpweb.food.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pfslibrary.DailyDiet;

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



    @RequestMapping(value = "/plan",method = RequestMethod.GET)
    public Solver getPlan() throws SQLException {
        Food food;

        int amount = 7;
        int period = 3;
        DailyDiet dailyDiet = new DailyDiet();;
        Solver s;
        food = foodService.getFood();

        s = dailyDiet.solve(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins());
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
       // food = ds.getFood();



        return s;
    }


}
