package com.drpweb.ingredient_on_food;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientOnFoodDao {
    List<IngredientOnFood> findAll();
    IngredientOnFood findOne(Long id);
    IngredientOnFood create(IngredientOnFood ingredientOnFood);
    IngredientOnFood update(IngredientOnFood ingredientOnFood);
    void delete(IngredientOnFood ingredientOnFood);
    List<IngredientOnFood> findByIngredientId(Long ingredientId);
    List<IngredientOnFood> findByFoodIndex(int id);
}
