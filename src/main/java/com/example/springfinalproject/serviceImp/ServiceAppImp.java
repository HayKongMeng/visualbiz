package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ServiceApp;
import com.example.springfinalproject.model.request.ServiceAppRequest;
import com.example.springfinalproject.model.request.ServiceScheduleRequest;
import com.example.springfinalproject.repository.CategoryRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.ServiceRepository;
import com.example.springfinalproject.repository.ShopRepository;
import com.example.springfinalproject.services.ServiceAppService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceAppImp implements ServiceAppService {
    private final ServiceRepository serviceRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    //get all service for customer
    @Override
    public List<ServiceApp> getAllService(Integer page, Integer size) {
        if (page >= 1){
            if(size >= 1){
                return serviceRepository.findAllService(page,size);
            }else throw new  BadRequestException("Page must be greater than 1");
        }else throw new BadRequestException("Size must be greater than 1");
    }


    //get all service by current user id
    @Override
    public List<ServiceApp> getAllServiceByCurrentUser() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = serviceRepository.findShopByUserId(userId);
        List<ServiceApp> serviceApps = serviceRepository.findAllServiceShop(shopId);
        System.out.println(shopId);
        if (shopId != null){
            if (!serviceApps.isEmpty()){
                return serviceApps;
            }else throw new NotFoundException("Services in your store doesn't exist");
        }else throw new BadRequestException("Please create shop first");
    }

    //get all service by shop id with pagination
    @Override
    public List<ServiceApp> getAllServiceByshopIdWithPagination(Integer shopId, Integer page, Integer size) {
        return serviceRepository.getAllServiceByshopIdWithPagination(shopId,page,size);
    }

    @Override
    public ServiceApp getServiceByIdForCustomer(Integer id) {
        if (id!=null) {
            return serviceRepository.getServiceByIdForCustomer(id);
        }else throw new NotFoundException("Service ID "+id+" dose not exist");
    }

    @Override
    public List<ServiceApp> getServiceByNameForCustomer(String name) {
        if (name != null){
            return serviceRepository.getServiceByNameForCustomer(name);
        }else throw new NotFoundException("Service NAME "+name+" dose not exist");
    }

    @Override
    public Integer updateServiceById(ServiceAppRequest serviceAppRequest,Integer id) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = serviceRepository.findShopByUserId(userId);
        System.out.println(shopId);
        Integer categoryId = serviceAppRequest.getCategoryId();
        if (shopId!=null){
            if (categoryRepository.getCategoryById(categoryId) != null){
                ServiceApp serviceApp = serviceRepository.findServiceById(id,shopId);
                System.out.println(serviceApp);
                if (serviceApp != null){
                    System.out.println(serviceApp.getServiceId());
                    if (serviceApp.getServiceId().equals(id)){
                        return serviceRepository.updateServiceById(serviceAppRequest,id);
                    }else throw new NotFoundException("Service ID " +id+ " does not exist for the current user's shop.");
                }else throw new NotFoundException("Service ID "+id+" does not exist for the current user's shop.");
            }else throw new NotFoundException("Category id "+categoryId+" dose not exist");
        } else {
            throw new BadRequestException("Please create a shop before updating products.");
        }
    }

    //delete service by id for current shop
    @Override
    public void deleteServiceForCurrentShop(List<Integer> id) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = serviceRepository.findShopByUserId(userId);

        if (shopId!=null){
            for (Integer productId : id){
                ServiceApp serviceApp = serviceRepository.findServiceById(productId,shopId);
                if (serviceApp != null){
                    serviceRepository.deleteServiceForCurrentShop(serviceApp.getServiceId());
                }else throw new NotFoundException("Service ID " +id+ " does not exist for the current user's shop.");
            }
        }else throw new BadRequestException("Please create a shop before deleting service.");
    }

    //get all service by category id service provider
    @Override
    public List<ServiceApp> getAllServiceByCategoryId(Integer id, Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = serviceRepository.findShopByUserId(userId);
        if (shopId!=null){
            if (id != null){
                return serviceRepository.getAllServiceByCategoryId(id,page,size,shopId);
            }else throw new NotFoundException("Category id "+id+" dose not exist for the current user's shop.");
        }else throw new BadRequestException("Please create a shop before get service.");
    }


    //get all service by category for customer
    @Override
    public List<ServiceApp> getAllServiceByCategoryIdForCustomer(Integer id, Integer page, Integer size) {
        if (id != null){
            return serviceRepository.getAllServiceByCategoryIdForCustomer(id,page,size);
        }else throw new NotFoundException("Category id "+id+" dose not exist.");
    }

    @Override
    public ServiceApp getServiceByIdInShop(Integer id) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        ServiceApp service = serviceRepository.findServiceById(id,shopId);
        if (shopId!=null){
            if (service != null) {
                return serviceRepository.findServiceById(id,shopId);
            }else throw new NotFoundException("Service with id " + id + " not exist");
        }else throw new BadRequestException("Please create a shop before get service.");
    }

    @Override
    public Integer postNewService(ServiceAppRequest serviceAppRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        Integer categoryId = serviceAppRequest.getCategoryId();
        if (shopId!=null){
            if (categoryRepository.getCategoryById(categoryId) != null){
                Integer serviceId = serviceRepository.insertNewService(serviceAppRequest);
                if (serviceId != null ){
                     serviceRepository.saveServiceShop(serviceId,shopId);
                }else throw new BadRequestException("Product id is null");
                return serviceId;
            }else throw new NotFoundException("Category with id " + categoryId + " not exist");
        }else throw new BadRequestException("Shop id is null");
    }

    @Override
    public List<ServiceApp> getServiceByName(String name,Integer page,Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId!=null){
            if (name != null){
                return serviceRepository.findServiceByName(name,shopId,page,size);
            }else throw new NotFoundException("Get service by name "+name+" not exist in your shop.");
        }else throw new BadRequestException("Please create shop first");
    }


    @Override
    public List<ServiceApp> getAllServiceByCategory(String categoryName) {
        List<ServiceApp> serviceList = serviceRepository.findServiceByCategory(categoryName);
        System.out.println(categoryName);
        System.out.println(serviceList);
        for (ServiceApp serviceApp : serviceList){
            if (serviceApp.getCategory().getCategoryName().equals(categoryName)) {
                return serviceList;
            }

        }
        throw new NotFoundException("Service with name : " + categoryName + " not exist");
    }

}
