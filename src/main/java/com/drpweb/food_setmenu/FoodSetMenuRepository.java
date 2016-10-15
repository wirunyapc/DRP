package com.drpweb.food_setmenu;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface FoodSetMenuRepository extends JpaRepository<FoodSetMenu,Long> {
    FoodSetMenu findBySetMenu_id(int setMenu_id);
}
