package com.drpweb.food_setmenu;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface FoodSetMenuDao {
    FoodSetMenu create(FoodSetMenu foodSetMenu);
    FoodSetMenu update (FoodSetMenu foodSetMenu);
    void delete(FoodSetMenu dietPlan);
    FoodSetMenu findOne(Long id);
    List<FoodSetMenu> findAll ();
    FoodSetMenu findBySetMenu_id(int id);
}
