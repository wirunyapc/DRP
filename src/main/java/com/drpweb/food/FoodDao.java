package com.drpweb.food;

import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodDao {
//    Food create(Food food);
//    Food update (Food food);
//    void delete(Food food);
    Food findOne(int id);
    List<Food> findAll ();
    Food findByFoodName(String name);
}
