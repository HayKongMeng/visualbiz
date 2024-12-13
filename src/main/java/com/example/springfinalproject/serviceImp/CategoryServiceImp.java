package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.request.CategoryRequest;
import com.example.springfinalproject.repository.CategoryRepository;
import com.example.springfinalproject.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> getAllCategories(Integer page, Integer size) {
        return categoryRepository.getAllCategories(page, size);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    @Override
    public Integer createCategory(CategoryRequest categoryRequest) {
        return categoryRepository.createCategory(categoryRequest);
    }

    @Override
    public Boolean deleteCategoryById(Integer categoryId) {
        return categoryRepository.deleteCategoryById(categoryId);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByName(categoryName);
    }

    @Override
    public Integer updateCategoryById(CategoryRequest categoryRequest, Integer categoryId) {
        return categoryRepository.updateCategoryById(categoryRequest,categoryId);
    }

    // get all categories by shop id
    @Override
    public List<Category> getAllCategoriesInShop(Integer shopId) {
        if (shopId!=null){
            return categoryRepository.getAllCategoriesInShop(shopId);
        }else throw new NotFoundException("Shop Id "+shopId+" does not exist");
    }
}
