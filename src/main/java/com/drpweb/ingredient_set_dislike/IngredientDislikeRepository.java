package com.drpweb.ingredient_set_dislike;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
public interface IngredientDislikeRepository extends JpaRepository<IngredientDislike,Long> {
    List<IngredientDislike> findByUserId(Long userId);
    IngredientDislike findByIngredientId(Long ingreId);
}
