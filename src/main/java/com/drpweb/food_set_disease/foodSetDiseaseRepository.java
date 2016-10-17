package com.drpweb.food_set_disease;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 10/17/2016.
 */
public interface FoodSetDiseaseRepository extends JpaRepository<FoodSetDisease,Long> {
    List<FoodSetDisease> findByDisease(Long diseaseId);
}
