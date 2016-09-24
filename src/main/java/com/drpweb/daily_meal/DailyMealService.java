package com.drpweb.daily_meal;

import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
public interface DailyMealService {
    List<DailyMeal> findByDietPlanId(Long id);
    List<DailyMeal> findByDate(Date date);
    void delete(Long dietPlanId);
}
