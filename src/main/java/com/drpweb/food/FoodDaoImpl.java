package com.drpweb.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
@Repository
public class FoodDaoImpl implements FoodDao{
    @Autowired
    FoodRepository foodRepository;
//    @Override
//    public Food create(Food food) {
//        return foodRepository.save(food);
//    }
//
//    @Override
//    public Food update(Food food) {
//        return foodRepository.save(food);
//    }
//
//    @Override
//    public void delete(Food food) {
//        foodRepository.delete(food);
//    }

    @Override
    public Food findOne(int id) {
        return foodRepository.findOne(id);
    }

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food findByFoodName(String name) {
        return foodRepository.findByFoodName(name);
    }
}
