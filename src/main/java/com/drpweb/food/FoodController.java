package com.drpweb.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Created by ADMIN on 10/13/2016.
 */
@CrossOrigin
@RestController
public class FoodController {
    @Autowired
    FoodDao foodDao;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getTotalDietCal",method = RequestMethod.GET)
    public int calculateDiet(@RequestParam("bfast")String bfast,@RequestParam("lunch")String lunch,@RequestParam("dinner")String dinner) throws SQLException {
        int totalDietCal = 0;
        Food foodBreakfast = foodDao.findByFoodName(bfast);
        Food foodLunch = foodDao.findByFoodName(lunch);
        Food foodDinner = foodDao.findByFoodName(dinner);
        totalDietCal = (foodBreakfast.getKal()+foodLunch.getKal())+foodDinner.getKal();
        System.out.println("totalDietcal" + totalDietCal);
        return  totalDietCal;
    }
}
