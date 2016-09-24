package com.drpweb.diet_plan;

import com.drpweb.user.User;

import java.sql.SQLException;

/**
 * Created by ADMIN on 9/12/2016.
 */
public interface DietPlanService {
    DietPlan findByUserId(Long id);
    void createPlan(String name) throws SQLException;
    String createPatientPlan(String name) throws SQLException;
    void savePlan(String result,User user);
    int getBmr(User user) throws SQLException;
    void updateUserDisease(Long diseaseId);
}
