package com.drpweb.ingredient_on_food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientOnFoodRepository extends JpaRepository<IngredientOnFood,Long> {
    List<IngredientOnFood> findByIngredientId(Long ingredientId);
}
