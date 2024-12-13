package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.ServiceApp;
import com.example.springfinalproject.model.request.ServiceAppRequest;
import com.example.springfinalproject.model.request.ServiceScheduleRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface ServiceAppService {
    // get all service for customer
    List<ServiceApp> getAllService(Integer page, Integer size);

    ServiceApp getServiceByIdInShop(Integer id);

    Integer postNewService(ServiceAppRequest serviceAppRequest);

    List<ServiceApp> getServiceByName(String name,Integer page, Integer size);

    List<ServiceApp> getAllServiceByCategory(String categoryName);

    //Get all service in shop for current user
    List<ServiceApp> getAllServiceByCurrentUser();

    //service get all service by shop id with pagination
    List<ServiceApp> getAllServiceByshopIdWithPagination(Integer shopId, Integer page, Integer size);

    ServiceApp getServiceByIdForCustomer(Integer id);

    List<ServiceApp> getServiceByNameForCustomer(String name);

    Integer updateServiceById(ServiceAppRequest serviceAppRequest ,Integer id);

    void deleteServiceForCurrentShop(List<Integer> id);

    List<ServiceApp> getAllServiceByCategoryId(Integer id, Integer page, Integer size);

    List<ServiceApp> getAllServiceByCategoryIdForCustomer(Integer id, Integer page, Integer size);
}

