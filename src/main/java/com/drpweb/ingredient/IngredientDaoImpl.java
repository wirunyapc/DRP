package com.drpweb.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 11/12/2016.
 */
@Repository
public class IngredientDaoImpl implements IngredientDao {
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient findOne(Long ingredientId) {
        return ingredientRepository.findOne(ingredientId);
    }

    @Override
    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Ingredient findByIngredientName(String name) {
        return ingredientRepository.findByIngredientName(name);
    }

    @Override
    public List<Ingredient> findByIdNotIn(List<Long> selectedIngredient) {
        return ingredientRepository.findByIdNotIn(selectedIngredient);
    }
}
