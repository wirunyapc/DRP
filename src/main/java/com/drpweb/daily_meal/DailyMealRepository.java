package com.drpweb.daily_meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 9/11/2016.
 */
public interface DailyMealRepository  extends JpaRepository<DailyMeal,Long> {
        List<DailyMeal> findByDietPlanId(Long id);
        List<DailyMeal> findByDate(Date date);

}
