package com.drpweb.diet_plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 9/12/2016.
 */
@Repository
public class DietPlanDaoImpl implements DietPlanDao{
    @Autowired
    DietPlanRepository dietPlanRepository;

    @Override
    public DietPlan create(DietPlan dietPlan) {
        return dietPlanRepository.save(dietPlan);
    }

    @Override
    public DietPlan update(DietPlan dietPlan) {
        return dietPlanRepository.save(dietPlan);
    }

    @Override
    public void delete(DietPlan dietPlan) {
        dietPlanRepository.delete(dietPlan);
    }

    @Override
    public DietPlan findOne(Long id) {
        return dietPlanRepository.findOne(id);
    }

    @Override
    public List<DietPlan> findAll() {
        return dietPlanRepository.findAll();
    }

    @Override
    public DietPlan findByUserId(Long id) {
        return dietPlanRepository.findByUserId(id);
    }
}
