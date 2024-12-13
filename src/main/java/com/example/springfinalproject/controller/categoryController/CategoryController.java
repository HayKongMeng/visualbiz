package com.example.springfinalproject.controller.categoryController;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.request.CategoryRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.CategoryResponse;
import com.example.springfinalproject.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/category")
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //get all category
    @GetMapping
    @Operation(summary = "Get all category")
    public ResponseEntity<CategoryResponse<List<Category>>> getAllCategories(@RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                             @RequestParam(name = "size",defaultValue = "4") Integer size) {
        CategoryResponse<List<Category>> response = CategoryResponse.<List<Category>>builder()
                .message("")
                .payload(categoryService.getAllCategories(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //get all category by shop id
    @GetMapping("/categoryInShop/{shopId}")
    @Operation(summary = "Get all category by shop id")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategoriesInShop(@PathVariable("shopId") @Positive Integer shopId){
        ApiResponse<List<Category>> response = ApiResponse.<List<Category>>builder()
                .message("Get all category by shop id "+shopId+" is successful.")
                .payload(categoryService.getAllCategoriesInShop(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    // get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse<Category>> getCategoryById(@PathVariable("id") Integer categoryId) {
        CategoryResponse<Category> response = null;
        if (categoryService.getCategoryById(categoryId) != null){
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .payload(categoryService.getCategoryById(categoryId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //get category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponse<Category>> getCategoryByName(@PathVariable("name") String categoryName) {
        CategoryResponse<Category> response = null;
        if (categoryService.getCategoryByName(categoryName) != null){
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .payload(categoryService.getCategoryByName(categoryName))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //post category
    @PostMapping()
    public ResponseEntity<CategoryResponse<Category>> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse<Category> response = null;
        Integer createId = categoryService.createCategory(categoryRequest);
        if (createId != null){
            response = CategoryResponse.<Category>builder()
                    .message("Create service is successful.")
                    .payload(categoryService.getCategoryById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse<Category>> deleteCategoryById(@PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = null;
        if (categoryService.deleteCategoryById(categoryId)){
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

    //update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse<Category>> updateCategoryById(@RequestBody CategoryRequest categoryRequest,
                                                                         @PathVariable("id") Integer categoryId){
        Integer updateId = categoryService.updateCategoryById(categoryRequest,categoryId);
        CategoryResponse<Category> response = null;
        if (updateId != null){
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .payload(categoryService.getCategoryById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = CategoryResponse.<Category>builder()
                    .message("")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }
}
