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
    private int setMenu_id;

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

    public int getSetMenu_id() {
        return setMenu_id;
    }

    public void setSetMenu_id(int setMenu_id) {
        this.setMenu_id = setMenu_id;
    }
}
