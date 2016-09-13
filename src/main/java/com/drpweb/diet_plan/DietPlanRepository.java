package com.drpweb.diet_plan;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 9/12/2016.
 */
public interface DietPlanRepository extends JpaRepository<DietPlan,Long> {
    DietPlan findByUserId(Long id);
}
