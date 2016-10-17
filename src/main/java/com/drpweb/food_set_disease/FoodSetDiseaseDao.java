package com.drpweb.food_set_disease;

import java.util.List;

/**
 * Created by ADMIN on 10/17/2016.
 */
public interface FoodSetDiseaseDao {
        List<FoodSetDisease> findByDisease(Long diseaseId);
}
