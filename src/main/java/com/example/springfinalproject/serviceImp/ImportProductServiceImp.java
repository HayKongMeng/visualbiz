package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.model.Entity.ImportProduct;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.response.ImportProductResponse;
import com.example.springfinalproject.repository.ImportProductRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.ShopRepository;
import com.example.springfinalproject.services.ImportProductService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportProductServiceImp implements ImportProductService {
    private final ImportProductRepository importProductRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;



    @Override
    public List<ImportProduct> getAllImportProduct() {
        return importProductRepository.getAllImportProduct();
    }

    @Override
    public ImportProduct getImportProductById(Integer id) {
        return importProductRepository.getImportByProductId(id);
    }

    @Override
    public Integer addNewImportProduct(Integer productId, ImportProductRequest importProductRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
//        List<Product> productList = productRepository.getProductByShopId(shopId);
//        List<Product> total_productQTY = new ArrayList<>();
//        List<ImportProductResponse> importProductResponses = new ArrayList<ImportProductResponse>();
//        for(Product product: productList){
//            ImportProductResponse importProductResponse = new ImportProductResponse();
//
//        }

        importProductRepository.addNewImportProduct(importProductRequest);
        return null;
    }

    @Override
    public Integer updateImportProduct(ImportProductRequest importProductRequest, Integer id) {
        return importProductRepository.updateImportProduct(importProductRequest,id);
    }

    @Override
    public boolean deleteImportProduct(Integer id) {
        return importProductRepository.deleteImportProduct(id);
    }

    @Override
    public void insertQty(ImportProductRequest importProductRequest) {
        importProductRepository.addNewImportProduct(importProductRequest);
    }

}
