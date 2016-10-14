package com.drpweb.food;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 8/20/2016.
 */
public interface FoodRepository extends JpaRepository<Food,Integer> {
//    String FIND_BY_DISEASE_QUERY = "SELECT f FROM Food WHERE food_id NOT IN(SELECT f.food_id FROM food f " +
//            "     LEFT JOIN ingredient_on_food iof " +
//            "       ON f.food_id = iof.food_id " +
//            "     LEFT JOIN food_set_disease fs " +
//            "       ON fs.ingredient_id = iof.ingredient_id " +
//            "     WHERE fs.disease_id = disease_id)";
//
//    @Query(FIND_BY_DISEASE_QUERY)
//    List<Food> findByDisease(String lastName);

    Food findByFoodName(String name);

}
