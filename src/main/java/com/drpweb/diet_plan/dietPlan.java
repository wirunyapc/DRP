package com.drpweb.diet_plan;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ADMIN on 9/11/2016.
 */
@Entity
@Table(name = "diet_plan")
public class DietPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long dietPlanId;
    private Date startDate;
    private Date endDate;
    @Column(name = "user_id")
    private Long userId;

//    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
//    @PrimaryKeyJoinColumn
//    private User user;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dietPlan")
//    private Set<DailyMeal> dailyMeals = new HashSet<DailyMeal>(0);
//
//    public Set<DailyMeal> getDailyMeals() {
//        return dailyMeals;
//    }
//
//    public void setDailyMeals(Set<DailyMeal> dailyMeals) {
//        this.dailyMeals = dailyMeals;
//    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Long getDietPlanId() {
        return dietPlanId;
    }

    public void setDietPlanId(Long dietPlanId) {
        this.dietPlanId = dietPlanId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
