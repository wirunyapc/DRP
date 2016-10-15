package com.drpweb.food_setmenu;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public class FoodSetMenuDaoImpl implements FoodSetMenuDao {
    @Autowired
    FoodSetMenuRepository foodSetMenuRepository;

    @Override
    public FoodSetMenu create(FoodSetMenu foodSetMenu) {
        return foodSetMenuRepository.save(foodSetMenu);
    }

    @Override
    public FoodSetMenu update(FoodSetMenu foodSetMenu) {
        return foodSetMenuRepository.save(foodSetMenu);
    }

    @Override
    public void delete(FoodSetMenu foodSetMenu) {
        foodSetMenuRepository.delete(foodSetMenu);
    }

    @Override
    public FoodSetMenu findOne(Long id) {
        return foodSetMenuRepository.findOne(id);
    }

    @Override
    public List<FoodSetMenu> findAll() {
        return foodSetMenuRepository.findAll();
    }

    @Override
    public FoodSetMenu findBySetMenu_id(int id) {
        return foodSetMenuRepository.findBySetMenu_id(id);
    }
}
