package com.example.springfinalproject.controller.sellerController;

import com.example.springfinalproject.exception.AlreadyCreateException;
import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ProductForSeller;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.request.ProductRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.ProductResponse;
import com.example.springfinalproject.services.ProductService;
import com.example.springfinalproject.utils.GetCurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.ibatis.annotations.Insert;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/seller/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //get all product
    @GetMapping()
    @Operation(summary = "Get all products (customer)")
    public ResponseEntity<ProductResponse<List<Product>>> getAllProducts(@Positive @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "8") Integer size) {

        ProductResponse<List<Product>> response = ProductResponse.<List<Product>>builder()
                .message("Get all products is successful.")
                .payload(productService.getAllProducts(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    // get product id for customer
    @GetMapping("/id/{id}")
    @Operation(summary = "Get product by id (customer)")
    public ResponseEntity<ApiResponse<Product>> getProductByIdForCustomer(@Valid @Positive @PathVariable("id") Integer id) {
        ApiResponse<Product> response = null;
        if (productService.getProductByIdForCustomer(id) != null) {
            response = ApiResponse.<Product>builder()
                    .message("Get product by id " + id + " is successful.")
                    .payload(productService.getProductByIdForCustomer(id))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("Product id " + id + " is not found");
    }

    //get product by id for seller
    @GetMapping("/productInShop/{id}")
    @Operation(summary = "Get product by id (seller)")
    public ResponseEntity<ProductResponse<Product>> getProductById(@Valid @PathVariable("id") @Positive Integer productId) {
        ProductResponse<Product> response = null;
        if (productService.getProductById(productId) != null) {
            response = ProductResponse.<Product>builder()
                    .message("Get product by id " + productId + " is successful.")
                    .payload(productService.getProductById(productId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else throw new NotFoundException("Product's id " + productId + " does not exist.");
        return ResponseEntity.ok(response);
    }

    //get product by name
    @GetMapping("/nameInShop/{name}")
    @Operation(summary = "Get product by name in shop (seller)")
    public ResponseEntity<ProductResponse<List<Product>>> getProductByName(@Valid @PathVariable("name") String productName,
                                                                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        ProductResponse<List<Product>> response = null;
        if (productService.getProductByName(productName, page, size) != null) {
            response = ProductResponse.<List<Product>>builder()
                    .message("Get product by name " + productName + " is successful.")
                    .payload(productService.getProductByName(productName, page, size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else throw new NotFoundException("Product's name " + productName + " does not exist.");
        return ResponseEntity.ok(response);
    }

    //get product by shop id
    @GetMapping("/shop/{shopId}")
    @Operation(summary = "Get product by shop id (customer)")
    public ResponseEntity<ApiResponse<List<Product>>> getProductByShopId(@PathVariable("shopId") @Positive Integer shopId,
                                                                         @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                                         @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        ApiResponse<List<Product>> response = null;
        List<Product> productList = productService.getProductByShopId(shopId, page, size);
        System.out.println(productList);
        if (productList != null) {
            response = ApiResponse.<List<Product>>builder()
                    .message("Get all product by shop id " + shopId + " is successful.")
                    .payload(productService.getProductByShopId(shopId, page, size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else throw new NotFoundException("Shop's id " + shopId + " does not exist.");
        return ResponseEntity.ok(response);
    }


    //get product by user Id
    @GetMapping("/currentShop")

    @Operation(summary = "Get all products by current shop (seller)")

    public ResponseEntity<ApiResponse<List<ProductForSeller>>> getAllProductInShop() {

        ApiResponse<List<ProductForSeller>> response = null;

        List<ProductForSeller> productList = productService.getAllProductInShop();

        System.out.println(" productService.getAllProductInShop()=" + productService.getAllProductInShop());

        System.out.println(productList);

        if (!productList.isEmpty()) {

            response = ApiResponse.<List<ProductForSeller>>builder()

                    .message("get all product by user id")

                    .payload(productList)

                    .httpStatus(HttpStatus.OK)

                    .timestamp(new Timestamp(System.currentTimeMillis()))

                    .build();

        } else {

            response = ApiResponse.<List<ProductForSeller>>builder()

                    .message("get all product by user id")

                    .httpStatus(HttpStatus.NOT_FOUND)

                    .timestamp(new Timestamp(System.currentTimeMillis()))

                    .build();

            return ResponseEntity.ok(response);

        }

        return ResponseEntity.ok(response);

    }

    //get product by category id for customer
    @GetMapping("/product-by-categoryId/{id}")
    @Operation(summary = "Get all product by category id (customer)")
    public ResponseEntity<ApiResponse<List<Product>>> getProductByCategoryIdForCustomer(@PathVariable("id") Integer categoryId,
                                                                                        @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                                                        @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        ApiResponse<List<Product>> response = null;
        List<Product> productList = productService.getProductByCategoryIdForCustomer(categoryId, page, size);
        if (productList != null && !productList.isEmpty()) {
            response = ApiResponse.<List<Product>>builder()
                    .message("get all product by category id " + categoryId + " is successful.")
                    .payload(productList)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("Product's category id " + categoryId + " does not exist.");
    }

    //get product by name customer
    @GetMapping("name/{name}")
    @Operation(summary = "Get all product by name (customer)")
    public ResponseEntity<ApiResponse<List<Product>>> getProductByNameForCustomer(@Valid @PathVariable("name") String name) {
        ApiResponse<List<Product>> response = null;
        List<Product> productList = productService.getProductByNameForCustomer(name);
        if (productList != null && !productList.isEmpty()) {
            response = ApiResponse.<List<Product>>builder()
                    .message("get all product by name " + name + " is successful.")
                    .payload(productList)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("Product's name " + name + " does not exist.");
    }

    //get product by category id
    @GetMapping("/getByCategoryId/{id}")
    @Operation(summary = "Get product by category id (seller)")
    public ResponseEntity<ProductResponse<List<Product>>> getProductByCategoryId(@PathVariable("id") @Positive Integer categoryId,
                                                                                 @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                                                 @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        ProductResponse<List<Product>> response;

        List<Product> productList = productService.getProductByCategoryId(categoryId, page, size);
        System.out.println(productList);
        if (productList != null && !productList.isEmpty()) {
            response = ProductResponse.<List<Product>>builder()
                    .message("Get product by category's id " + categoryId + " is successful.")
                    .payload(productService.getProductByCategoryId(categoryId, page, size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            response = ProductResponse.<List<Product>>builder()
                    .message(categoryId + " does not exist")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //get product by category name
    @GetMapping("/getByCategoryName/{name}")
    @Operation(summary = "Get product by category name")
    public ResponseEntity<ProductResponse<List<Product>>> getProductByCategoryId(@Valid @PathVariable("name") String categoryName,
                                                                                 @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        ProductResponse<List<Product>> response;
        List<Product> productList = productService.getProductByCategoryName(categoryName, page, size);
        if (productList != null && !productList.isEmpty()) {
            response = ProductResponse.<List<Product>>builder()
                    .message("Get product by category' name " + categoryName + " is successful.")
                    .payload(productService.getProductByCategoryName(categoryName, page, size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            response = ProductResponse.<List<Product>>builder()
                    .message(categoryName + " does not exist.")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

    //update product by id
    @PutMapping("/{id}")
    @Operation(summary = "Update product (seller)")
    public ResponseEntity<ProductResponse<Product>> updateProduct(@Valid @RequestBody ProductRequest productRequest,
                                                                  @PathVariable("id") Integer productId) {
        Integer updateId = productService.updateProductById(productRequest, productId);
        ProductResponse<Product> response = null;
        if (updateId != null) {
            response = ProductResponse.<Product>builder()
                    .message("Update product by id " + updateId + " is successful.")
                    .payload(productService.getProductById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("Update by id " + updateId + " not found.");
    }

    //delete product by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product (seller)")
    public ResponseEntity<ProductResponse<Product>> deleteProduct(@PathVariable("id")Integer productId) {
        productService.deleteProduct(productId);
        ProductResponse<Product> response = ProductResponse.<Product>builder()
                .message("Delete product id " + productId + " is successful.")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //post product
    @PostMapping()
    @Operation(summary = "Post product")
    public ResponseEntity<ProductResponse<Product>> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse<Product> response = null;
        Integer createId = productService.createProduct(productRequest);
//        System.out.println("=================" + productService.getProductById(createId));
        System.out.println(createId);
        if (createId != null) {
            response = ProductResponse.<Product>builder()
                    .message("Post product is successfully")
                    .payload(productService.getProductById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else throw new AlreadyCreateException("create product failed");
    }

    @PostMapping("/import/{barcode}")
    @Operation(summary = "Import product by barcode (seller)")
    public ResponseEntity<ApiResponse<Product>> getProductByImportId(@RequestBody ImportProductRequest importProductRequest, @PathVariable("barcode") String barcode) {

        ApiResponse<Product> response = null;
        Product barCode = productService.importProductByBarcode(importProductRequest, barcode);
        if (barCode != null) {
            response = ApiResponse.<Product>builder()
                    .message("get all product by import id " + barcode)
                    .payload(productService.getProductByBarcode(barcode))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("");
    }

    @GetMapping("barcode/{barcode}")
    @Operation(summary = "Get product by barcode (seller)")
    public ResponseEntity<ApiResponse<Product>> getProductByBarcode(@PathVariable("barcode") String barcode) {
        ApiResponse<Product> response = null;
        if (productService.getProductByBarcode(barcode) != null) {
            response = ApiResponse.<Product>builder()
                    .message("get product by barcode " + barcode + " successful")
                    .payload(productService.getProductByBarcode(barcode))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        } else throw new NotFoundException("Get product by barcode " + barcode + " not found.");
    }
}
