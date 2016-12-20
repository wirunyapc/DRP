package com.drpweb.ingredient_on_food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
@Repository
public class IngredientOnFoodDaoImpl implements IngredientOnFoodDao{
    @Autowired
    IngredientOnFoodRepository ingredientOnFoodRepository;

    @Override
    public List<IngredientOnFood> findAll() {
        return ingredientOnFoodRepository.findAll();
    }

    @Override
    public IngredientOnFood findOne(Long id) {
        return ingredientOnFoodRepository.findOne(id);
    }

    @Override
    public IngredientOnFood create(IngredientOnFood ingredientOnFood) {
        return ingredientOnFoodRepository.save(ingredientOnFood);
    }

    @Override
    public IngredientOnFood update(IngredientOnFood ingredientOnFood) {
        return ingredientOnFoodRepository.save(ingredientOnFood);
    }

    @Override
    public void delete(IngredientOnFood ingredientOnFood) {
        ingredientOnFoodRepository.delete(ingredientOnFood);
    }

    @Override
    public List<IngredientOnFood> findByIngredientId(Long ingredientId) {
        return ingredientOnFoodRepository.findByIngredientId(ingredientId);
    }

    @Override
    public List<IngredientOnFood> findByFoodIndex(int id) {
        return ingredientOnFoodRepository.findByFoodIndex(id);
    }
}
