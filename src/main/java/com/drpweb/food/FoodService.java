package com.drpweb.food;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodService {
    Food create(Food food);
    Food update (Food food);
    void delete(int id);
    Food findOne(int id);
    List<Food> findAll ();
    Food findByName(String name);
    Food getFood() throws SQLException;
}
