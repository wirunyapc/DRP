package com.drpweb.caloriesInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Entity
public class CalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double budget;
    private double diet;
    private double activity;
    private double net;
    private double under;

    public CalInfo() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getDiet() {
        return diet;
    }

    public void setDiet(double diet) {
        this.diet = diet;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getUnder() {
        return under;
    }

    public void setUnder(double under) {
        this.under = under;
    }
}
