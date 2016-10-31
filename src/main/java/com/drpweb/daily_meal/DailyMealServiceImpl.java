package com.drpweb.daily_meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
@Service
public class DailyMealServiceImpl implements DailyMealService{
    @Autowired
    DailyMealDao dailyMealDao;


    @Override
    public void delete(Long dietPlanId) {


    List<DailyMeal> dailyMeals = dailyMealDao.findByDietPlanId(dietPlanId);
        for (DailyMeal d: dailyMeals) {
            dailyMealDao.delete(d);        }
    }


}
