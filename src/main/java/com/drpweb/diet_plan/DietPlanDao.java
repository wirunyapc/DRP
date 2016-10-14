package com.drpweb.diet_plan;

/**
 * Created by ADMIN on 9/12/2016.
 */
public interface DietPlanDao {
    DietPlan create(DietPlan dietPlan);
//    DietPlan update (DietPlan dietPlan);
//    void delete(DietPlan dietPlan);
//    DietPlan findOne(Long id);
//    List<DietPlan> findAll ();
    DietPlan findByUserId(Long id);
}
