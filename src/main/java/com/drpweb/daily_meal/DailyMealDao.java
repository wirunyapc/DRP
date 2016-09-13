package com.drpweb.daily_meal;

import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
public interface DailyMealDao {
    DailyMeal create(DailyMeal dailyMeal);
    DailyMeal update (DailyMeal dailyMeal);
    void delete(DailyMeal dailyMeal);
    DailyMeal findOne(Long id);
    List<DailyMeal> findAll ();
    List<DailyMeal> findByDietPlanId(Long id);
    List<DailyMeal> findByDate(Date date);
}
