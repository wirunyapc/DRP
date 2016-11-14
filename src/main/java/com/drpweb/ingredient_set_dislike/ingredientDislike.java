package com.drpweb.ingredient_set_dislike;

import javax.persistence.*;

/**
 * Created by ADMIN on 11/13/2016.
 */
@Entity
@Table(name = "ingredient_set_dislike")
public class IngredientDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "ingredient_id")
    private Long ingredientId;

    public IngredientDislike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
