package com.drpweb.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category findOne(int id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByCategoryId(int id) {
        return categoryRepository.findByCategoryId(id);
    }

    @Override
    public Category findByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }
}
