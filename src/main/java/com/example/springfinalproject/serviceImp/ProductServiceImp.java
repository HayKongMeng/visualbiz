package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.ImportProductWithId;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ProductForSeller;
import com.example.springfinalproject.model.request.ImportProductRequest;
import com.example.springfinalproject.model.request.ProductRequest;
import com.example.springfinalproject.repository.CategoryRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.PromotionRepository;
import com.example.springfinalproject.services.ProductService;
import com.example.springfinalproject.utils.GetCurrentUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, PromotionRepository promotionRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;
    }

    //get all product
    @Override
    public List<Product> getAllProducts(Integer page, Integer size) {
        if (page >= 1) {
            if (size >= 1) {
                return productRepository.getAllProduct(page, size);
            }
            throw new BadRequestException("Size must be greater than 1");
        } else throw new BadRequestException("Page must be greater than 1");
    }

    //get product id
    @Override
    public Product getProductById(Integer productId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            if (productId != null) {
                return productRepository.getProductById(productId, shopId);
            } else throw new NotFoundException("Product id " + productId + " does not exist");
        } else throw new BadRequestException("Please create shop.");
    }

    //get product by product name
    @Override
    public List<Product> getProductByName(String productName, Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            if (productName != null) {
                return productRepository.getProductByName(productName, shopId, page, size);
            } else throw new NotFoundException("Product name " + productName + " is not match");
        } else throw new BadRequestException("Please create shop");
    }

    //get product by category id
    @Override
    public List<Product> getProductByCategoryId(Integer categoryId, Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            if (categoryId != null) {
                return productRepository.getProductByCategoryId(categoryId, shopId, page, size);
            } else throw new NotFoundException("Category id " + categoryId + " is not match");
        } else throw new BadRequestException("Please create shop");
    }

    //create product
    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        Integer categoryId = productRequest.getCategoryId();
        if (shopId != null) {
            if (categoryRepository.getCategoryById(categoryId) != null) {
                Integer productId = productRepository.createProduct(productRequest);

                System.out.println(productRequest);

                if (productId != null) {
                    productRepository.saveProductShop(productId, shopId);
                } else throw new BadRequestException("Product id is null");
                return productId;
            } else throw new BadRequestException("Category id " + categoryId + " dose not exist");
        } else throw new BadRequestException("Please create shop");
    }

    //get product by category name
    @Override
    public List<Product> getProductByCategoryName(String categoryName, Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            if (categoryName != null) {
                return productRepository.getProductByCategoryName(categoryName, shopId, page, size);
            } else throw new BadRequestException("Category name " + categoryName + " is not match");
        } else throw new BadRequestException("Please create shop");
    }

    //update product by id
    @Override
    public Integer updateProductById(ProductRequest productRequest, Integer productId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        Integer categoryId = productRequest.getCategoryId();
        if (shopId != null) {
            System.out.println("shopId is " + shopId);
            if (categoryRepository.getCategoryById(categoryId) != null) {
                Product products = productRepository.getProductById(productId, shopId);
                if (products != null) {
                    if (products.getProductId().equals(productId)) {
                        return productRepository.updateProductById(productRequest, productId);
                    } else
                        throw new NotFoundException("Product ID " + productId + " does not exist for the current user's shop.");
                } else
                    throw new NotFoundException("Product ID " + productId + " does not exist for the current user's shop.");
            } else throw new NotFoundException("Category id " + categoryId + " dose not exist");
        } else {
            throw new BadRequestException("Please create a shop before updating products.");
        }
    }

    //delete product
    @Override
    public void deleteProduct(Integer productId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        boolean getStatus = productRepository.getProductStatus(productId);

        if (shopId != null) {
            if (getStatus) {
                productRepository.deleteProduct(productId);
            } else {
                productRepository.updateProductToTrue(productId);
            }
        } else throw new BadRequestException("Please create shop");
    }

    //get product by shop id
    @Override
    public List<Product> getProductByShopId(Integer shopId, Integer page, Integer size) {
        return productRepository.getProductByShopIdWithPageSize(shopId, page, size);
    }

    @Override
    public List<ProductForSeller> getAllProductInShop() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if(shopId == null){
            throw new BadRequestException( shopId + " not found");
        }
        List<Product> productList = productRepository.getAllProductByShopId(shopId);
        if(productList==null){
            throw new NotFoundException("product not found");
        }

        List<ProductForSeller> productForSellerList  = new ArrayList<>();
        System.out.println("productForSellerList " + productForSellerList);
//
        for(Product product: productList){
            Category category  = categoryRepository.getCategoryById(product.getCategory().getCategoryId());
            if(category==null){
                throw new NotFoundException("Category not found");
            }
            Double discountPercentage = promotionRepository.productPercentage(product.getProductId());
            if(discountPercentage==null) {
                discountPercentage = 0.0d;
            }
//            private Integer productId;
//            private String productName;
//            private Double unitPrice;
//            private Double discount;
//            private String productDescription;
//            private Integer productQty;
//            private String productImg;
//            private LocalDateTime expireDate;
//            private boolean isActive;
//            private String barCode;
//            private Category category;
            ProductForSeller productForSeller = new ProductForSeller(
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    discountPercentage,
                    product.getProductDescription(),
                    product.getProductQty(),
                    product.getProductImg(),
                    product.getExpireDate(),
                    product.isActive(),
                    product.getBarCode(),
                    category
            );
            productForSellerList.add(productForSeller);
        }
        System.out.println("productForSellerList" + productForSellerList);

        return productForSellerList;

    }


    //get all product by category id customer
    @Override
    public List<Product> getProductByCategoryIdForCustomer(Integer categoryId, Integer page, Integer size) {
        if (categoryId != null) {
            return productRepository.getProductByCategoryIdForCustomer(categoryId, page, size);
        } else throw new NotFoundException("Category id " + categoryId + " is not exist.");
    }

    @Override
    public List<Product> getProductByNameForCustomer(String name) {
        if (name != null) {
            return productRepository.getProductByNameForCustomer(name);
        } else throw new NotFoundException("Product name " + name + " dose not exist.");
    }

    @Override
    public Product importProductByBarcode(ImportProductRequest importProductRequest, String barcode) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        Product productReturn = null;

        if (shopId != null) {
            int qtyOldStock = 0;
            //select all product that exist in shop
            productRepository.getProductByShopId(shopId);
            Product productOld = productRepository.getProductByBarcode(barcode, shopId);
            qtyOldStock = productOld.getProductQty() + importProductRequest.getProductQty();
            //update product here
            productReturn = productRepository.updateProductByBarcode(qtyOldStock, barcode);
            ImportProductWithId importId = productRepository.saveImportProuct(importProductRequest);
            productRepository.saveProductImportDetail(productOld.getProductId(), importId.getImportId());

        } else throw new BadRequestException("Please create shop");

        return productReturn;
    }

    @Override
    public Product getProductByBarcode(String barcode) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        Product productId = null;
        if (shopId != null) {
            productId = productRepository.getProductByBarcode(barcode, shopId);
            if (productId != null) {
                return productRepository.getProductById(productId.getProductId(), shopId);
            } else throw new NotFoundException("Product id " + productId + " dose not exist.");
        } else throw new BadRequestException("Shop id (" + userId + ") dose not exist");
    }

    @Override
    public Product getProductByIdForCustomer(Integer id) {
        System.out.println(id);
        if (id != null) {
            System.out.println(productRepository.getProductByIdForCustomer(id));
            return productRepository.getProductByIdForCustomer(id);
        } else throw new NotFoundException("Product id is " + id + " not found.");
    }
}
