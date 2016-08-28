package com.drpweb.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodDao foodDao;


    @Override
    public Food create(Food food) {
        return foodDao.create(food);
    }

    @Override
    public Food update(Food food) {
        return foodDao.update(food);
    }

    @Override
    public void delete(Long id) {
        foodDao.delete(foodDao.findOne(id));
    }

    @Override
    public Food findOne(Long id) {
        return foodDao.findOne(id);
    }

    @Override
    public List<Food> findAll() { return foodDao.findAll(); }

    @Override
    public Food findByName(String name) {
        return foodDao.findByFoodName(name);
    }

    @Override
    public Food getFood(){
        Food food = new Food();
        List<Food> foods = foodDao.findAll();
        int size = foods.size();
        int[]arr_id = new int[size];
        int[]arr_kal = new int[size];
        int[]arr_fat = new int[size];
        int[]arr_carboh = new int[size];
        int[]arr_protein = new int[size];

        ArrayList<int[]> kals = new ArrayList();
        ArrayList<int[]> fats = new ArrayList();
        ArrayList<int[]> carbohs = new ArrayList();
        ArrayList<int[]> proteins = new ArrayList();
        ArrayList<String> names = new ArrayList();

        int idx =0;
        for(Food f : foods){
            arr_id[idx] = f.getFoodId();
            arr_kal[idx] = f.getKal();
            arr_fat[idx] = f.getFat();
            arr_carboh[idx] = f.getCarboh();
            arr_protein[idx] = f.getProtein();

            kals.add(new int[]{arr_id[idx], arr_kal[idx]});
            fats.add(new int[]{arr_id[idx], arr_fat[idx]});
            carbohs.add(new int[]{arr_id[idx], arr_carboh[idx]});
            proteins.add(new int[]{arr_id[idx], arr_protein[idx]});
            names.add(f.getFoodName());

            idx++;
//            food.getKals().add(f.getArr_kal());
        }
        food.setArr_id(arr_id);
        food.setArr_kal(arr_kal);
        food.setArr_fat(arr_fat);
        food.setArr_carboh(arr_carboh);
        food.setArr_protein(arr_protein);
        food.setKals(kals);
        food.setFats(fats);
        food.setCarbohs(carbohs);
        food.setProteins(proteins);
        food.setNames(names);

        return food;
    }
    /*@Override
    public Food getFood() throws SQLException{
        DailyDiet dailyDiet = new DailyDiet();
        ResultSet rs;
        Statement stmt;
        PreparedStatement prepareStmt;
        String query;
        Food food;

            rs = dailyDiet.getAllFood();
        int size = dailyDiet.getResultSize();
        food = new Food();
        int[]arr_id = new int[size];
        int[]arr_kal = new int[size];
        int[]arr_fat = new int[size];
        int[]arr_carboh = new int[size];
        int[]arr_protein = new int[size];

        ArrayList<int[]> kals = new ArrayList();
        ArrayList<int[]> fats = new ArrayList();
        ArrayList<int[]> carbohs = new ArrayList();
        ArrayList<int[]> proteins = new ArrayList();
        ArrayList<String> names = new ArrayList();

        int idx = 0;
        while(rs.next()){
            arr_id[idx] = rs.getInt("food_id");
            arr_kal[idx] = rs.getInt("food_kcal");
            arr_fat[idx] = rs.getInt("fat");
            arr_carboh[idx] = rs.getInt("carbohydate");
            arr_protein[idx] = rs.getInt("protein");

            kals.add(new int[]{arr_id[idx], arr_kal[idx]});
            fats.add(new int[]{arr_id[idx], arr_fat[idx]});
            carbohs.add(new int[]{arr_id[idx], arr_carboh[idx]});
            proteins.add(new int[]{arr_id[idx], arr_protein[idx]});
            names.add(rs.getString("ff"));

            idx++;
        }

        food.setArr_id(arr_id);
        food.setArr_kal(arr_kal);
        food.setArr_fat(arr_fat);
        food.setArr_carboh(arr_carboh);
        food.setArr_protein(arr_protein);
        food.setKals(kals);
        food.setFats(fats);
        food.setCarbohs(carbohs);
        food.setProteins(proteins);
        food.setNames(names);

        return food;
    }*/
}
