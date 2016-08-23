package com.drpweb.food;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by ADMIN on 8/20/2016.
 */
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long foodPatientTypeId;
    private String foodPatientTypeName;
    private String foodName;
    private int kal;
    private int fat;
    private int carboh;
    private int protein;
    private int categoriesId;
    private String categoriesName;
    private int amount;
    private String unit;

    private int[]arr_id;
    private int[]arr_kal;
    private int[]arr_fat;
    private int[]arr_carboh;
    private int[]arr_protein;

    private ArrayList<int[]> kals;
    private ArrayList<int[]> fats;
    private ArrayList<int[]> carbohs;
    private ArrayList<int[]> proteins;
    private ArrayList<String> names;




    public Food() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodPatientTypeId() {
        return foodPatientTypeId;
    }

    public void setFoodPatientTypeId(Long foodPatientTypeId) {
        this.foodPatientTypeId = foodPatientTypeId;
    }

    public String getFoodPatientTypeName() {
        return foodPatientTypeName;
    }

    public void setFoodPatientTypeName(String foodPatientTypeName) {
        this.foodPatientTypeName = foodPatientTypeName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getKal() {
        return kal;
    }

    public void setKal(int kal) {
        this.kal = kal;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarboh() {
        return carboh;
    }

    public void setCarboh(int carboh) {
        this.carboh = carboh;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int[] getArr_id() {
        return arr_id;
    }

    public void setArr_id(int[] arr_id) {
        this.arr_id = arr_id;
    }

    public int[] getArr_kal() {
        return arr_kal;
    }

    public void setArr_kal(int[] arr_kal) {
        this.arr_kal = arr_kal;
    }

    public int[] getArr_fat() {
        return arr_fat;
    }

    public void setArr_fat(int[] arr_fat) {
        this.arr_fat = arr_fat;
    }

    public int[] getArr_carboh() {
        return arr_carboh;
    }

    public void setArr_carboh(int[] arr_carboh) {
        this.arr_carboh = arr_carboh;
    }

    public int[] getArr_protein() {
        return arr_protein;
    }

    public void setArr_protein(int[] arr_protein) {
        this.arr_protein = arr_protein;
    }

    public ArrayList<int[]> getKals() {
        return kals;
    }

    public void setKals(ArrayList<int[]> kals) {
        this.kals = kals;
    }

    public ArrayList<int[]> getFats() {
        return fats;
    }

    public void setFats(ArrayList<int[]> fats) {
        this.fats = fats;
    }

    public ArrayList<int[]> getCarbohs() {
        return carbohs;
    }

    public void setCarbohs(ArrayList<int[]> carbohs) {
        this.carbohs = carbohs;
    }

    public ArrayList<int[]> getProteins() {
        return proteins;
    }

    public void setProteins(ArrayList<int[]> proteins) {
        this.proteins = proteins;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
