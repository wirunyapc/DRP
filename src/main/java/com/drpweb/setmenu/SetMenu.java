package com.drpweb.setmenu;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by ADMIN on 10/14/2016.
 */
@Entity
@Table(name = "setmenu")
public class SetMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int setMenu_id;
    private int total_cal;
    private int total_fat;
    private int total_carboh;
    private int total_protein;

    @Transient
    private int[]arr_setMenu_id;
    @Transient
    private int[]arr_total_cal;
    @Transient
    private int[]arr_total_fat;
    @Transient
    private int[]arr_total_carboh;
    @Transient
    private int[]arr_total_protein;

    @Transient
    private ArrayList<int[]> total_cals;
    @Transient
    private ArrayList<int[]> total_fats;
    @Transient
    private ArrayList<int[]> total_carbohs;
    @Transient
    private ArrayList<int[]> total_proteins;
//    @Transient
//    private ArrayList<String> names;
public SetMenu() {
}

    public int getSetMenu_id() {
        return setMenu_id;
    }

    public void setSetMenu_id(int setMenu_id) {
        this.setMenu_id = setMenu_id;
    }

    public int getTotal_cal() {
        return total_cal;
    }

    public void setTotal_cal(int total_cal) {
        this.total_cal = total_cal;
    }

    public int getTotal_fat() {
        return total_fat;
    }

    public void setTotal_fat(int total_fat) {
        this.total_fat = total_fat;
    }

    public int getTotal_carboh() {
        return total_carboh;
    }

    public void setTotal_carboh(int total_carboh) {
        this.total_carboh = total_carboh;
    }

    public int getTotal_protein() {
        return total_protein;
    }

    public void setTotal_protein(int total_protein) {
        this.total_protein = total_protein;
    }

    public int[] getArr_setMenu_id() {
        return arr_setMenu_id;
    }

    public void setArr_setMenu_id(int[] arr_setMenu_id) {
        this.arr_setMenu_id = arr_setMenu_id;
    }

    public int[] getArr_total_cal() {
        return arr_total_cal;
    }

    public void setArr_total_cal(int[] arr_total_cal) {
        this.arr_total_cal = arr_total_cal;
    }

    public int[] getArr_total_fat() {
        return arr_total_fat;
    }

    public void setArr_total_fat(int[] arr_total_fat) {
        this.arr_total_fat = arr_total_fat;
    }

    public int[] getArr_total_carboh() {
        return arr_total_carboh;
    }

    public void setArr_total_carboh(int[] arr_total_carboh) {
        this.arr_total_carboh = arr_total_carboh;
    }

    public int[] getArr_total_protein() {
        return arr_total_protein;
    }

    public void setArr_total_protein(int[] arr_total_protein) {
        this.arr_total_protein = arr_total_protein;
    }

    public ArrayList<int[]> getTotal_cals() {
        return total_cals;
    }

    public void setTotal_cals(ArrayList<int[]> total_cals) {
        this.total_cals = total_cals;
    }

    public ArrayList<int[]> getTotal_fats() {
        return total_fats;
    }

    public void setTotal_fats(ArrayList<int[]> total_fats) {
        this.total_fats = total_fats;
    }

    public ArrayList<int[]> getTotal_carbohs() {
        return total_carbohs;
    }

    public void setTotal_carbohs(ArrayList<int[]> total_carbohs) {
        this.total_carbohs = total_carbohs;
    }

    public ArrayList<int[]> getTotal_proteins() {
        return total_proteins;
    }

    public void setTotal_proteins(ArrayList<int[]> total_proteins) {
        this.total_proteins = total_proteins;
    }
}
