package com.drpweb.food;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodService {
    Food create(Food food);
    Food update (Food food);
    void delete(Long id);
    Food findOne(Long id);
    List<Food> findAll ();
    Food findByName(String name);
    Food getFood() throws SQLException;
}
