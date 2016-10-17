package com.drpweb.food_set_disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ADMIN on 10/17/2016.
 */
@Entity
@Table(name = "food_set_disease")
public class FoodSetDisease {
    @Id
    @Column(name = "id")
    private Long foodDiseaseId;

    @Column(name = "disease_id")
    private Long disease;

    @Column(name = "food_id")
    private int food;

    public Long getDisease() {
        return disease;
    }

    public FoodSetDisease() {
    }




    public void setDisease(Long disease) {
        this.disease = disease;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public Long getFoodDiseaseId() {
        return foodDiseaseId;
    }

    public void setFoodDiseaseId(Long foodDiseaseId) {
        this.foodDiseaseId = foodDiseaseId;
    }
}
