package com.drpweb;

import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ADMIN on 9/15/2016.
 */
public class DailyMealServiceImplTest {

    @Test
    public void delete(){
        DailyMealDao dailyMealDao = mock(DailyMealDao.class);
        List<DailyMeal> dailyMeals = new ArrayList<>();

        when(dailyMealDao.findByDietPlanId(1L)).thenReturn(dailyMeals);
        for (DailyMeal d: dailyMeals) {
            dailyMealDao.delete(d);
        }
        assertThat(dailyMealDao.findByDietPlanId(1L).size(),is(0));
    }
}