package com.drpweb.category;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface CategoryDao {
    Category create(Category category);
    Category update (Category category);
    void delete(Category category);
    Category findOne(int id);
    List<Category> findAll();
    Category findByCategoryId(int id);
    Category findByCategoryName(String name);
}
