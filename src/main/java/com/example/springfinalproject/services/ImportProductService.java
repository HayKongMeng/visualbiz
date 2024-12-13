package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.ImportProduct;
import com.example.springfinalproject.model.request.ImportProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImportProductService {
    List<ImportProduct> getAllImportProduct();
    ImportProduct getImportProductById(Integer id);
    Integer addNewImportProduct(Integer productId, ImportProductRequest importProductRequest);
    Integer updateImportProduct(ImportProductRequest importProductRequest, Integer id);
    boolean deleteImportProduct(Integer id);
    void insertQty(ImportProductRequest importProductRequest);


}
