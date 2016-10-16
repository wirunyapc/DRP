package com.drpweb.food_setmenu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface FoodSetMenuRepository extends JpaRepository<FoodSetMenu,Long> {
    List<FoodSetMenu> findBySetmenu(int setMenu_id);
}
