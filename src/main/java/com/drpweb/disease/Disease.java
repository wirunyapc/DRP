package com.drpweb.disease;

import javax.persistence.*;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "disease_id")
    private Long id;
    private String diseaseName;
    private int cals;
    private int fat;
    private int carboh;
    private int protein;


    public Disease() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public double getKcal() {
        return cals;
    }

    public void setKcal(int cals) {
        this.cals = cals;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public double getCarboh() {
        return carboh;
    }

    public void setCarboh(int carboh) {
        this.carboh = carboh;
    }

    public double getProt() {
        return protein;
    }

    public void setProt(int protein) {
        this.protein = protein;
    }
}
