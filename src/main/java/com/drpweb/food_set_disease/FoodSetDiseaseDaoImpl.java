package com.drpweb.food_set_disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 10/17/2016.
 */
@Repository
public class FoodSetDiseaseDaoImpl implements FoodSetDiseaseDao {
    @Autowired
    FoodSetDiseaseRepository foodSetDiseaseRepositorty;
    @Override
    public List<FoodSetDisease> findByDisease(Long diseaseId) {
        return foodSetDiseaseRepositorty.findByDisease(diseaseId);
    }

    @Override
    public FoodSetDisease create(FoodSetDisease foodSetDisease) {
        return foodSetDiseaseRepositorty.save(foodSetDisease);
    }

    @Override
    public void delete(FoodSetDisease foodSetDisease) {
        foodSetDiseaseRepositorty.delete(foodSetDisease);
    }


}
