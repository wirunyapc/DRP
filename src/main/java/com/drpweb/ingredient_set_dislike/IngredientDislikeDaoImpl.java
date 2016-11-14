package com.drpweb.ingredient_set_dislike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
@Repository
public class IngredientDislikeDaoImpl implements IngredientDislikeDao {
    @Autowired
    IngredientDislikeRepository ingredientDislikeRepository;

    @Override
    public List<IngredientDislike> findAll() {
        return ingredientDislikeRepository.findAll();
    }

    @Override
    public IngredientDislike create(IngredientDislike ingredientDislike) {
        return ingredientDislikeRepository.save(ingredientDislike);
    }

    @Override
    public IngredientDislike update(IngredientDislike ingredientDislike) {
        return ingredientDislikeRepository.save(ingredientDislike);
    }

    @Override
    public void delete(IngredientDislike ingredientDislike) {
        ingredientDislikeRepository.delete(ingredientDislike);
    }

    @Override
    public List<IngredientDislike> findByUserId(Long userId) {
        return ingredientDislikeRepository.findByUserId(userId);
    }

    @Override
    public IngredientDislike findByIngredientId(Long ingreId) {
        return ingredientDislikeRepository.findByIngredientId(ingreId);
    }
}
