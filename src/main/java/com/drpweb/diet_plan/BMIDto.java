package com.drpweb.diet_plan;

/**
 * Created by ADMIN on 9/13/2016.
 */
public class BMIDto {
    public String bmi;
    public BMIDto(){
        this.bmi = "null";
    }
    public BMIDto(String bmi) {
        this.bmi = bmi;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
}
