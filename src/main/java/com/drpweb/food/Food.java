package com.drpweb.food;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by ADMIN on 8/20/2016.
 */
@Entity
@Table(name = "foodforset")
public class Food{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_id")
    private int foodId;
    //private int fpTypeId;
    @Transient
    private String foodPatientTypeName;
    private String foodName;
    private int foodKcal;
    private int fat;
    private int carbohydate;
    private int protein;
    private int categoriesId;
    @Transient
    private String categoriesName;
    private int amount;
    private String unit;

//    @OneToMany(mappedBy="food", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<DailyMeal> dailyMeals;

    @Transient
    private int[]arr_id;
    @Transient
    private int[]arr_kal;
    @Transient
    private int[]arr_fat;
    @Transient
    private int[]arr_carboh;
    @Transient
    private int[]arr_protein;

    @Transient
    private ArrayList<int[]> kals;
    @Transient
    private ArrayList<int[]> fats;
    @Transient
    private ArrayList<int[]> carbohs;
    @Transient
    private ArrayList<int[]> proteins;
    @Transient
    private ArrayList<String> names;

    public Food() {
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
        return foodKcal;
    }

    public void setKal(int kal) {
        this.foodKcal = kal;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarboh() {
        return carbohydate;
    }

    public void setCarboh(int carboh) {
        this.carbohydate = carboh;
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

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

//    public int getFoodPatientTypeId() {
//        return fpTypeId;
//    }
//
//    public void setFoodPatientTypeId(int foodPatientTypeId) {
//        this.fpTypeId = foodPatientTypeId;
//    }

  /*  @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
    private Set<DailyMeal> dailyMeals = new HashSet<DailyMeal>(0);

    public Set<DailyMeal> getDailyMeals() {
        return dailyMeals;
    }

    public void setDailyMeals(Set<DailyMeal> dailyMeals) {
        this.dailyMeals = dailyMeals;
    }*/
}
