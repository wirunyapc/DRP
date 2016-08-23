package com.drpweb.diet_plan;

import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import com.drpweb.food.FoodService;
import engine.DietSolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.FoodControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 8/22/2016.
 */
@CrossOrigin
@RestController
public class DietPlanController {
    @Autowired
    FoodService foodService;
    beans.Food food;
    List<IntegerVariable[][]> varList = new ArrayList<>();
    int amount = 0;
    int period = 0;

    @RequestMapping(value = "/diet_plan/plan",method = RequestMethod.GET)
    public Solver getPlan(){
        FoodControl foodCtrl = new FoodControl();
        food = foodCtrl.getAllFood();

        Solver s;

        DietSolve ds = new DietSolve();
        amount = 7;
        period = 3;


        s = ds.solve(amount, period, food);
        varList = ds.getVars();
        food = ds.getFood();

        if(s!=null){
            System.out.print("I'm foods!");
        }

        return s;
    }


}
