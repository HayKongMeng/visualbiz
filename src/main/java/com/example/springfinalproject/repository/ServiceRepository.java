package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.ServiceApp;
import com.example.springfinalproject.model.request.ServiceAppRequest;
import com.example.springfinalproject.model.request.ServiceScheduleRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServiceRepository {

    //get all service for customer
    @Select("SELECT * FROM service_tb LIMIT #{size} OFFSET ${size} * (#{page}-1)")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "serviceId", column = "service_id"),
                    @Result(property = "serviceName", column = "service_name"),
                    @Result(property = "servicePrice", column = "service_price"),
                    @Result(property = "serviceDescription", column = "service_description"),
                    @Result(property = "serviceImage", column = "service_image"),
                    @Result(property = "serviceStatus", column = "is_active"),
                    @Result(property = "category", column = "category_id",
                            one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById")
                    )
            }
    )
    List<ServiceApp> findAllService(Integer page, Integer size);

    //find all service by shop id
    @Select("""
SELECT s.* from service_tb s
INNER JOIN service_shop_tb sp on s.service_id = sp.service_id
where shop_id = #{shopId}
""")
    @ResultMap("mapper")
    List<ServiceApp> findAllServiceShop(Integer shopId);

    //find service by id base on current shop
    @Select("""
            SELECT s.* FROM service_tb s
                                INNER JOIN service_shop_tb ss ON s.service_id = ss.service_id
            WHERE s.service_id = #{serviceId} and shop_id = #{shopId};
            """)
    @ResultMap("mapper")
    ServiceApp findServiceById(Integer serviceId, Integer shopId);

    //create service
    @Select("INSERT INTO service_tb(service_name, service_price, service_description,service_image, category_id) " +
            "VALUES (#{rq.serviceName}, #{rq.servicePrice}, #{rq.serviceDescription},#{rq.serviceImg}, #{rq.categoryId}) " +
            "RETURNING service_id")
    Integer insertNewService(@Param("rq") ServiceAppRequest serviceAppRequest);

    //save service and shop
    @Select("INSERT INTO service_shop_tb(service_id, shop_id) " +
            "VALUES (#{serviceId}, #{shopId})")
    void saveServiceShop(Integer serviceId, Integer shopId);

    @Select("""
SELECT * from service_tb p
INNER JOIN service_shop_tb ss on p.service_id = ss.service_id
where service_name ILIKE #{name} || '%' and shop_id = #{shopId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<ServiceApp> findServiceByName(String name,Integer shopId,Integer page,Integer size);


    @Select("""
                SELECT shop_id from shop_tb  where user_id = #{userId}
            """)
    Integer findShopByUserId(Integer userId);


    @Select("""
                SELECT * from service_tb s
                inner join category_tb ct on s.category_id = ct.category_id
                where  category_name = #{categoryName}
            """)
    @ResultMap("mapper")
    List<ServiceApp> findServiceByCategory(String categoryName);

    //get all service by shop id with pagination
    @Select("""
SELECT s.* from service_tb s
INNER JOIN service_shop_tb ss on s.service_id = ss.service_id
where shop_id = #{shopId} LIMIT #{size} OFFSET ${size} * (#{page}-1)
""")
    @ResultMap("mapper")
    List<ServiceApp> getAllServiceByshopIdWithPagination(Integer shopId, Integer page, Integer size);

    //get service by id for customer
    @Select("""
SELECT *
 from service_tb
 where service_id = #{serviceId};
""")
    @ResultMap("mapper")
    ServiceApp getServiceByIdForCustomer(Integer serviceId);

    //get service by name for customer
    @Select("""
SELECT * from service_tb where service_name ILIKE #{name} || '%'
""")
    @ResultMap("mapper")
    List<ServiceApp> getServiceByNameForCustomer(String name);

    //update service in shop
    @Select("""
UPDATE service_tb set service_name = #{rq.serviceName},
                      service_price = #{rq.servicePrice},
                      service_description = #{rq.serviceDescription},
                      service_image = #{rq.serviceImg},
                      category_id = #{rq.categoryId},
                      is_active= #{rq.isActive}
where service_id = #{id} RETURNING service_id;
""")
    Integer updateServiceById(@Param("rq") ServiceAppRequest serviceAppRequest, Integer id);

    @Update("""
UPDATE service_tb SET is_active = false
where service_id = #{id}
""")
    boolean deleteServiceForCurrentShop(Integer id);

    //get all service by category id service provider
    @Select("""
select * from service_tb s
INNER JOIN service_shop_tb ss on s.service_id = ss.service_id
where category_id = #{categoryId} and shop_id = #{shopId} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<ServiceApp> getAllServiceByCategoryId(Integer categoryId, Integer page, Integer size, Integer shopId);


    //get all service by category id customer
    @Select("""
SELECT * from service_tb s
INNER JOIN service_shop_tb ps on s.service_id = ps.service_id
where category_id = #{id} LIMIT #{size} OFFSET ${size}*(#{page}-1)
""")
    @ResultMap("mapper")
    List<ServiceApp> getAllServiceByCategoryIdForCustomer(Integer id, Integer page, Integer size);
 //service provider ?

    @Select("""
SELECT st.service_id,st.service_name, st.service_price, st.service_description, st.service_image,st.is_active
 from service_tb st 
 inner join service_shop_tb sst on st.service_id=sst.service_id where sst.service_id = #{serviceId} AND sst.shop_id =#{shopId};
""")
    @ResultMap("mapper")
    ServiceApp getAllServiceForShop(Integer serviceId,Integer shopId);

}
