package com.drpweb.ingredient_on_food;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientOnFoodRepository extends JpaRepository<IngredientOnFood,Long> {
    IngredientOnFood findByIngredientId(Long ingredientId);
}
