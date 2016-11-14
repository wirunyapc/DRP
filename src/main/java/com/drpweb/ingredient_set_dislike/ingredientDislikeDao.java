package com.drpweb.ingredient_set_dislike;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientDislikeDao {
    List<IngredientDislike> findAll();
    IngredientDislike create(IngredientDislike ingredientDislike);
    IngredientDislike update(IngredientDislike ingredientDislike);
    void delete(IngredientDislike ingredientDislike);
    List<IngredientDislike> findByUserId(Long userId);
    IngredientDislike findByIngredientId(Long ingreId);
}
