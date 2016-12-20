package com.drpweb.food_set_disease;

import javax.persistence.*;

/**
 * Created by ADMIN on 10/17/2016.
 */
@Entity
@Table(name = "food_set_disease")
public class FoodSetDisease {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodDiseaseId;

    @Column(name = "disease_id")
    private Long disease;

    @Column(name = "setmenu_id")
    private int setmenu;



    public Long getDisease() {
        return disease;
    }

    public FoodSetDisease() {
    }




    public void setDisease(Long disease) {
        this.disease = disease;
    }


    public Long getFoodDiseaseId() {
        return foodDiseaseId;
    }

    public void setFoodDiseaseId(Long foodDiseaseId) {
        this.foodDiseaseId = foodDiseaseId;
    }

    public int getSetmenu() {
        return setmenu;
    }

    public void setSetmenu(int setmenu) {
        this.setmenu = setmenu;
    }
}
