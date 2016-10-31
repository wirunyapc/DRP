package com.drpweb;

import com.drpweb.food.Food;
import com.drpweb.food.FoodDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ADMIN on 9/15/2016.
 */
public class FoodServiceImplTest {

    @Test
    public void getFood() throws Exception {

        Food food = mock(Food.class);

        FoodDao foodDao = mock(FoodDao.class);
        List<Food> foods = new ArrayList<>();

        when(foodDao.findAll()).thenReturn(foods);

        int[] arr_id = new int[3];
        int[] arr_kal = new int[3];
        int[] arr_fat = new int[3];
        int[] arr_carboh = new int[3];
        int[] arr_protein = new int[3];

        ArrayList<int[]> kals = new ArrayList();
        ArrayList<int[]> fats = new ArrayList();
        ArrayList<int[]> carbohs = new ArrayList();
        ArrayList<int[]> proteins = new ArrayList();
        ArrayList<String> names =new ArrayList();
        names.add("Nam prik kapi");
        names.add("Tod man pla");
        names.add("Egg sausage");


        when(food.getArr_id()).thenReturn(arr_id);
        when(food.getArr_kal()).thenReturn(arr_kal);
        when(food.getArr_carboh()).thenReturn(arr_carboh);
        when(food.getArr_protein()).thenReturn(arr_protein);
        when(food.getArr_id()).thenReturn(arr_id);
        when(food.getKals()).thenReturn(kals);
        when(food.getFats()).thenReturn(fats);
        when(food.getCarbohs()).thenReturn(carbohs);
        when(food.getProteins()).thenReturn(proteins);
        when(food.getNames()).thenReturn(names);


        assertThat(food.getNames().get(0),is("Nam prik kapi"));
        assertThat(food.getNames().get(1),is("Tod man pla"));
        assertThat(food.getNames().get(2),is("Egg sausage"));



    }



}