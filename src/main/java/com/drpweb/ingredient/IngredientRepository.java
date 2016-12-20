package com.drpweb.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 11/12/2016.
 */
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    Ingredient findByIngredientName(String name);
    List<Ingredient> findByIdNotIn(List<Long> selectedIngredient);
    Ingredient findById(Long id);
}
