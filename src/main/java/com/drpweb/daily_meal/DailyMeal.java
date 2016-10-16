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
    @Column(name = "setmenu_id")
    private int setMenu_id;
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


    public Long getDietPlanId() {
        return dietPlanId;
    }

    public void setDietPlanId(Long dietPlanId) {
        this.dietPlanId = dietPlanId;
    }


    public int getSetMenu_id() {
        return setMenu_id;
    }

    public void setSetMenu_id(int setMenu_id) {
        this.setMenu_id = setMenu_id;
    }
}
