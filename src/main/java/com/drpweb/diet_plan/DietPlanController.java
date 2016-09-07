package com.drpweb.diet_plan;

import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import com.drpweb.food.Food;
import com.drpweb.food.FoodService;
import com.drpweb.util.DailyDiet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
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



    @RequestMapping(value = "/plan",method = RequestMethod.GET)
    public Solver getPlan() throws SQLException {
        Food food;

        int amount = 7;
        int period = 3;
        DailyDiet dailyDiet = new DailyDiet();
        Solver s;
        food = foodService.getFood();

        s = dailyDiet.solve(amount, period,
                food.getArr_id(), food.getArr_kal(),food.getArr_fat(), food.getArr_carboh(), food.getArr_protein(),
                food.getKals(), food.getFats(), food.getCarbohs(), food.getProteins());
        List<IntegerVariable[][]> varList = dailyDiet.getVars();
       // food = ds.getFood();

        ObjectMapper mapper = new ObjectMapper();
        List plan = dailyDiet.plan;
//        final String stringify = mapper.writeValueAsString(dailyDiet.plan.toString());
        return s;
    }
    public void writeListToJsonArray() throws IOException {
        final List<Event> list = new ArrayList<Event>(2);

//        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

//        mapper.writeValue(out, list);

//        final byte[] data = out.toByteArray();
        System.out.println(mapper.writeValueAsString(list));
    }

    @RequestMapping(value = "/bmi",method = RequestMethod.GET)
    public String getBmi(@RequestParam("weight")double weight, @RequestParam("height")double height) throws SQLException {
        DailyDiet dd = new DailyDiet();
        String result = dd.calBMI(weight, height);
        System.out.print("result :"+result);

        return result;
    }


}
