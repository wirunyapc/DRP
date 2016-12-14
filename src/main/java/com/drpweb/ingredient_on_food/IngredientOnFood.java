package com.drpweb.ingredient_on_food;

import javax.persistence.*;

/**
 * Created by ADMIN on 11/13/2016.
 */
@Entity
@Table(name = "ingredient_on_food")
public class IngredientOnFood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ingredient_id")
    private Long ingredientId;
    @Column(name = "food_index")
    private int foodIndex;
    @Column(name = "ingredient_amont")
    private double ingredientAmount;
    @Column(name = "unit")
    private String unit;

    public IngredientOnFood() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getFoodIndex() {
        return foodIndex;
    }

    public void setFoodIndex(int foodIndex) {
        this.foodIndex = foodIndex;
    }

    public double getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(double ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
