package com.drpweb.food;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodService {

    Food getFood() throws SQLException;
    Food getFoodByCal(int cal) throws SQLException;
    Food listToFood(List<Food> foods);
}
