package com.drpweb.category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByCategoryId(int id);
    Category findByCategoryName(String name);
}
