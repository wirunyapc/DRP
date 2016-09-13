package com.drpweb.daily_meal;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ADMIN on 8/20/2016.
 */
@Entity
@Table(name = "daily_meal")
public class DailyMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Date date;
    private Long mealId;
    private int foodId;
    private Long dietPlanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long meal) {
        this.mealId = meal;
    }

//    public Long getFoodId() {
//        return foodId;
//    }
//
//    public void setFoodId(Long foodId) {
//        this.foodId = foodId;
//    }
//
//    public Long getDietPlanId() {
//        return dietPlanId;
//    }
//
//    public void setDietPlanId(Long dietPlanId) {
//        this.dietPlanId = dietPlanId;
//    }

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false, insertable = false, updatable = false)
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private DietPlan dietPlan;

    public DietPlan getDietPlan() {
        return dietPlan;
    }

    public void setDietPlan(DietPlan dietPlan) {
        this.dietPlan = dietPlan;
    }*/

    public Long getDietPlanId() {
        return dietPlanId;
    }

    public void setDietPlanId(Long dietPlanId) {
        this.dietPlanId = dietPlanId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}