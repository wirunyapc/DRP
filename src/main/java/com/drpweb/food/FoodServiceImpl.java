package com.drpweb.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.FoodControl;

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
    public beans.Food getFood(){
        FoodControl foodCtrl = new FoodControl();
        beans.Food foods;
        foods = foodCtrl.getAllFood();
        System.out.print(foods.getFoodId());
        return foods;
    }
}
