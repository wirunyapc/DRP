package com.drpweb.ingredient_on_food;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientOnFoodDao {
    List<IngredientOnFood> findAll();
    IngredientOnFood create(IngredientOnFood ingredientOnFood);
    IngredientOnFood update(IngredientOnFood ingredientOnFood);
    void delete(IngredientOnFood ingredientOnFood);
    IngredientOnFood findByIngredientId(Long ingredientId);
}
