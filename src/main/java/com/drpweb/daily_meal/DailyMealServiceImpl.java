package com.drpweb.daily_meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
@Service
public class DailyMealServiceImpl implements DailyMealService{
    @Autowired
    DailyMealDao dailyMealDao;

    @Override
    public List<DailyMeal> findByDietPlanId(Long id) {
        return dailyMealDao.findByDietPlanId(id);
    }

    @Override
    public List<DailyMeal> findByDate(Date date) {
        return dailyMealDao.findByDate(date);
    }

//    @Override
//    public List<DailyMeal> getByPlan(DietPlan dietPlan) {
//        Set<DailyMeal> dailymeals = dietPlan.getDailyMeals();
//        return dailyMealDao.;
//    }
}
