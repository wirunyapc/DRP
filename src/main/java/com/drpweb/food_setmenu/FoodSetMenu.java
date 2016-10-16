package com.drpweb.food_setmenu;

import javax.persistence.*;

/**
 * Created by ADMIN on 10/14/2016.
 */
@Entity
@Table(name = "food_setmenu")
public class FoodSetMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long food_setMenu_id;
    @Column(name = "food_id")
    private int foodId;
    @Column(name = "setmenu_id")
    private int setmenu;
    @Column(name = "foodIdx")
    private int foodIndex;

    public Long getFood_setMenu_id() {
        return food_setMenu_id;
    }

    public void setFood_setMenu_id(Long food_setMenu_id) {
        this.food_setMenu_id = food_setMenu_id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }


    public int getFoodIndex() {
        return foodIndex;
    }

    public void setFoodIndex(int foodIndex) {
        this.foodIndex = foodIndex;
    }

    public int getSetmenu() {
        return setmenu;
    }

    public void setSetmenu(int setmenu) {
        this.setmenu = setmenu;
    }
}
