package com.example.springfinalproject.controller.importProductController;

import com.example.springfinalproject.model.Entity.ImportProduct;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.ImportProductResponse;
import com.example.springfinalproject.model.response.ProductResponse;
import com.example.springfinalproject.services.ImportProductService;
import com.example.springfinalproject.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/importProduct")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@CrossOrigin
public class ImportProductController {
    private final ImportProductService importProductService;
    private final ProductService productService;


    @GetMapping
    public List<ImportProduct> getAllImportProduct(){
        return importProductService.getAllImportProduct();
    }

    @GetMapping("/{id}")
    public ImportProduct getImportProduct(@Positive Integer id){
        return importProductService.getImportProductById(id);
    }
    @PostMapping
    public ResponseEntity<ImportProductResponse<ImportProduct>> postNewImportProduct(@Positive Integer productId,@RequestBody @Valid ImportProductRequest importProductRequest){
        ImportProductResponse<ImportProduct> response;
        Integer createId=importProductService.addNewImportProduct(productId, importProductRequest);
        if(createId!=null) {
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("Import Product created")
                    .payload(importProductService.getImportProductById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        }else {
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("Promotion not created")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportProductResponse<ImportProduct>> editImportProductById(@RequestBody @Valid ImportProductRequest importProductRequest, @Positive Integer id){
        ImportProductResponse<ImportProduct> response = null;
        if (importProductService.getImportProductById(id)!=null){
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("Get import product by id "+id)
                    .payload(importProductService.getImportProductById(id))
                    .httpStatus(HttpStatus.OK)
                    .build();
        }else {
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("Get import product by id "+id+" not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

    //get import product by product id
//    @GetMapping("/product/{product-id}")
//    @Operation(summary = "get product by shop id")
//    public ResponseEntity<ImportProductResponse<List<ImportProduct>>> getProductByImportId(@PathVariable("product-id") Integer productId) {
//        ImportProductResponse<List<ImportProduct>> response;
//
//
//        List<ImportProduct> importProduct = Arrays.asList(importProductService.getImportByProductId(productId));
////        importProductService.saveImportProduct(productId,importId);
//
//        if (importProduct != null && !importProduct.isEmpty()) {
//            response = ImportProductResponse.<List<ImportProduct>>builder()
//                    .message("get all product by import id " + productId)
//                    .payload(importProduct)
//                    .httpStatus(HttpStatus.OK)
//                    .build();
//        } else {
//            response = ImportProductResponse.<List<ImportProduct>>builder()
//                    .message("get all product by import id " + productId + " not found")
//                    .payload(new ArrayList<>())
//                    .httpStatus(HttpStatus.NOT_FOUND)
//                    .build();
//        }
//        return ResponseEntity.ok(response);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImportProductResponse<ImportProduct>> deleteImportProduct(@Positive Integer id){
        ImportProductResponse<ImportProduct> response = null;
        if (importProductService.deleteImportProduct(id)){
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }else {
            response = ImportProductResponse.<ImportProduct>builder()
                    .message("not successfully")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

}
