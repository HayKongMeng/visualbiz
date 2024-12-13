package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.dto.PromotionDto;
import com.example.springfinalproject.model.dto.PromotionTb;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.PromotionRequest;
import com.example.springfinalproject.model.response.PromotionResponse;
import com.example.springfinalproject.repository.CategoryRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.PromotionRepository;
import com.example.springfinalproject.repository.RoleRepository;
import com.example.springfinalproject.services.PromotionService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PromotionServiceImp implements PromotionService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<PromotionTbWithId> getAllPromotion(Integer page, Integer size) {
        if (page >= 1 ){
            if (size >= 1){
                return promotionRepository.getAllPromotion(page, size);
            }else throw new BadRequestException("Invalid size number");
        }else throw new BadRequestException("Invalid page number");
    }


    @Override
    public List<PromotionResponse> createPromotionForProduct(PromotionRequest promotionRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId == null) {
            throw new BadRequestException("Please create shop first");
        }

        if (promotionRequest.getStartDate().isAfter(promotionRequest.getEndDate())) {
            throw new BadRequestException("Invalid promotion date");
        }

        System.out.println("Product IDs: " + promotionRequest.getProductIds());
        List<Product> productList1 = new ArrayList<>();
        for (Integer productIdRequest : promotionRequest.getProductIds()) {

            Product product = productRepository.getProductById(productIdRequest, shopId);
            if (product == null) {
                throw new NotFoundException("Product with ID " + productIdRequest + " not found in shop " + shopId);
            }
            productList1.add(product);
        }

        if (productList1.isEmpty()) {
            throw new NotFoundException("No products found for the given IDs: " + promotionRequest.getProductIds());
        }

        List<PromotionResponse> promotionResponseList = new ArrayList<>();

        for (Product product : productList1) {
            Category category = categoryRepository.getCategoryByProductId(product.getProductId());
            if (category == null) {
                throw new NotFoundException("Category not found for product ID " + product.getProductId());
            }

            double discountPrice = product.getUnitPrice() - (product.getUnitPrice() * promotionRequest.getPercentage() / 100);
            PromotionTb promotionTb = new PromotionTb(
                    promotionRequest.getPercentage(),
                    promotionRequest.getStartDate(),
                    promotionRequest.getEndDate(),
                    shopId,
                    promotionRequest.getPromotionTitle(),
                    promotionRequest.getPromotionDescription(),
                    promotionRequest.getPromotionImage(),
                    false,
                    discountPrice
            );
            PromotionTbWithId promotionTb1 = promotionRepository.saveToPromotionTb(promotionTb);
            if (promotionTb1 == null) {
                throw new BadRequestException("Failed to save promotion details.");
            }

            promotionRepository.savePromotionProduct(promotionTb1.getPromotionId(), product.getProductId());

            PromotionResponse promotionResponse = new PromotionResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    promotionRequest.getPercentage(),
                    discountPrice,
                    promotionTb1.getPromotionDescription(),
                    product.getProductQty(),
                    promotionTb1.getPromotionImage(),
                    promotionTb1.getStartDate(),
                    true,
                    category
            );
            promotionResponseList.add(promotionResponse);
        }
        return promotionResponseList;
    }


    @Override
    public PromotionDto getPromotionById(Integer promotionId) {
        if (promotionId!=null) {
            return promotionRepository.getPromotionById(promotionId);
        }else throw new  NotFoundException("Promotion ID "+ promotionId +" dose not exist.");
    }

    @Override
    public Integer updatePromotionById(PromotionRequest promotionRequest, Integer promotionId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            List<Product> productList = productRepository.getProductByShopId(shopId);
        }
        List<Product> promotionProductList = promotionRepository.getPromotionById(promotionId).getProductList();
        promotionRepository.deletePromotionProduct(promotionId);
        System.out.println(promotionProductList);
        if (promotionId != null) {
            promotionRepository.updatePromotionById(promotionRequest, promotionId);
            for (Integer productId : promotionRequest.getProductIds()) {
                promotionRepository.updatePromotionById(promotionRequest, promotionId);
                promotionRepository.savePromotionProduct(promotionId, productId);

            }
        }
        return promotionId;
    }

    @Override
    public List<PromotionTbWithId> getPromotionShopId(Integer shopId, Integer page, Integer size) {

        List<PromotionTbWithId> promotionTbWithIdList = promotionRepository.getPromotionShopId(shopId,page,size);
        if(promotionTbWithIdList.isEmpty()){
            throw new NotFoundException("Promotion not found");
        }
        return promotionTbWithIdList;
    }

    @Override
    public List<PromotionTbWithId> getOldesPromotion() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if(shopId==null){
            throw new BadRequestException("Please create shop first");
        }
        List<PromotionTbWithId> promotionTbWithIdList = promotionRepository.getOldestPromotion(shopId);
        if(promotionTbWithIdList.isEmpty()){
            throw new NotFoundException("Promotion not found");
        }
        return promotionTbWithIdList;
    }

    @Override
    public List<PromotionTbWithId> getThisWeekPromotion() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if(shopId==null){
            throw new BadRequestException("Please create shop first");
        }
        List<PromotionTbWithId> promotionTbWithIdList = promotionRepository.getThisWeekPromotion(shopId);
        if(promotionTbWithIdList.isEmpty()){
            throw new NotFoundException("Promotion not found");
        }
        return promotionTbWithIdList;
    }

    @Override
    public PromotionTbWithId deletePromotionWithId(Integer promotionId) {
        if( promotionId < 0 ) {
            throw new BadRequestException( promotionId +" should be positive");
        }
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if(shopId==null){
            throw new BadRequestException("Please create shop first");
        }
        PromotionTbWithId promotionTbWithIdDeleted = promotionRepository.deletePromotionWithId(promotionId);
        System.out.println("promotionTbWithIdDeleted " + promotionTbWithIdDeleted);

        return promotionTbWithIdDeleted;
    }

    @Override
    public List<PromotionTbWithId> getPromotionCurrentShop(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        Integer roleId = roleRepository.getRoleIdForServiceProvider(userId);
        if(roleId == null){
            throw new BadRequestException( "user with id ="+  userId + " not a service provider" +
                    "So can not find services that have promotion");
        }

        if(shopId==null){
            throw new BadRequestException("Please create shop first");
        }
        List<PromotionTbWithId> promotionTbWithIdList = promotionRepository.getPromotionShopId(shopId,page,size);
        if(promotionTbWithIdList.isEmpty()){
            throw new NotFoundException("Promotion not found");
        }
        return promotionTbWithIdList;
    }


}
