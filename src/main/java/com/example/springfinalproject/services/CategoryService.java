package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories(Integer page, Integer size);
    Category getCategoryById(Integer categoryId);
    Integer createCategory(CategoryRequest categoryRequest);
    Boolean deleteCategoryById(Integer categoryId);
    Category getCategoryByName(String categoryName);
    Integer updateCategoryById(CategoryRequest categoryRequest,Integer categoryId);

    List<Category> getAllCategoriesInShop(Integer shopId);
}
