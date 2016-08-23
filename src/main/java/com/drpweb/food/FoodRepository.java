package com.drpweb.food;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodRepository extends JpaRepository<Food,Long> {
    Food findByFoodName(String name);
}
