package com.drpweb.ingredient;

import java.util.List;

/**
 * Created by ADMIN on 11/12/2016.
 */
public interface IngredientDao {
    List<Ingredient> findAll();
    Ingredient findOne(Long ingredientId);
    Ingredient create(Ingredient ingredient);
    Ingredient update(Ingredient ingredient);
    void delete(Ingredient ingredient);
    Ingredient findByIngredientName(String name);
    List<Ingredient> findByIdNotIn(List<Long> selectedIngredient);
}
