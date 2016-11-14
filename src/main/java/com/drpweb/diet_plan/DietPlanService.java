package com.drpweb.diet_plan;

import com.drpweb.user.User;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by ADMIN on 9/12/2016.
 */
public interface DietPlanService {
    DietPlan findByUserId(Long id);
    //void setDietPlanDate(DietPlan dietPlan);
    void createPlan(String name) throws SQLException;
    String createPatientPlan(String name) throws SQLException;
    void savePlan(String result,User user);
    int getBmr(double weight, double height, int age, String gender) throws SQLException;
    int getAge(Date db) throws SQLException;
    Date parseDate(String date);
}
