package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ProductForSeller;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.request.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts(Integer page,Integer size);
    Product getProductById(Integer productId);
    List<Product> getProductByName(String productName,Integer page,Integer size);
    List<Product> getProductByCategoryId(Integer categoryId,Integer page,Integer size);
    Integer createProduct(ProductRequest productRequest);
    List<Product> getProductByCategoryName(String categoryName,Integer page,Integer size);

    Integer updateProductById(ProductRequest productRequest, Integer productId);

    void deleteProduct(Integer productId);

    List<Product> getProductByShopId(Integer shopId,Integer page,Integer size);

    List<ProductForSeller> getAllProductInShop();

    List<Product> getProductByCategoryIdForCustomer(Integer categoryId, Integer page, Integer size);

    List<Product> getProductByNameForCustomer(String name);

    Product importProductByBarcode(ImportProductRequest importProductRequest, String barcode);

    Product getProductByBarcode(String barcode);

    Product getProductByIdForCustomer(Integer id);


//    void saveProductImport(Integer productId, Integer importId);
}