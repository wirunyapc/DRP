package com.drpweb.daily_meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
@Repository
public class DailyMealDaoImpl implements DailyMealDao{
    @Autowired
    DailyMealRepository dailyMealRepository;

    @Override
    public DailyMeal create(DailyMeal dailyMeal) {
        return dailyMealRepository.save(dailyMeal);
    }

    @Override
    public DailyMeal update(DailyMeal dailyMeal) {
        return dailyMealRepository.save(dailyMeal);
    }

    @Override
    public void delete(DailyMeal dailyMeal) {
        dailyMealRepository.delete(dailyMeal);
    }

//    @Override
//    public DailyMeal findOne(Long id) {
//        return dailyMealRepository.findOne(id);
//    }
//
//    @Override
//    public List<DailyMeal> findAll() {
//        return dailyMealRepository.findAll();
//    }

    @Override
    public List<DailyMeal> findByDietPlanId(Long id) {
        return dailyMealRepository.findByDietPlanId(id);
    }

}
