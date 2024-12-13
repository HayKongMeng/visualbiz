package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.dto.BookTb;
import com.example.springfinalproject.model.response.ServicePromotionResponse;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ServiceProviderRepository {

    //get all promotion for service provider with userId and roleId
    @Results(
            id = "serviceMapper",
            value = {
                    @Result(property = "serviceId", column = "service_id"),
                    @Result(property = "serviceName", column = "service_name"),
                    @Result(property = "promotionTitle", column = "promotion_title"),
                    @Result(property = "unitPrice", column = "service_price"),
                    @Result(property = "discountPercent", column = "percentage"),
                    @Result(property = "discountUnitPrice", column = "discount_price"),
                    @Result(property = "serviceDescription", column = "promotion_description"),
                    @Result(property = "serviceImg", column = "service_image"),
                    @Result(property = "startDate", column = "start_date"),
                    @Result(property = "expireDate", column = "end_date"),
                    @Result(property = "isActive", column = "is_expire"),
                    @Result(property = "category", column = "category_id" ,
                    one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById"))
            }
    )
    @Select("""
    SELECT * FROM promotion_tb promotion
     INNER JOIN service_promotion_tb spt on promotion.promotion_id = spt.promotion_id
     INNER JOIN service_tb st on st.service_id = spt.service_id
     WHERE promotion.shop_id = #{shopId}
     LIMIT #{size} OFFSET #{size}*(#{page}-1);
""")
    List<ServicePromotionResponse> getAllServicePromotionForServiceProvider(Integer page, Integer size, Integer shopId);

    @Select("""
    SELECT * FROM book_tb WHERE shop_id =#{shopId}
""")
    @Result(property = "bookId", column = "book_id")
    @Result(property = "statusId", column = "status_id")
    @Result(property = "userId", column = "user_id")
    @Result(property = "shopId", column = "shop_id")
    List<BookTb> getAllBookForAShop(Integer shopId);

    @Select("""
    SELECT st.status_type FROM status_tb st
    WHERE ( st.status_id = (
    SELECT bt.status_id FROM book_tb bt
    WHERE bt.book_id = #{bookId})
    );
""")
    String getBookStatus(Integer bookId);

//    @Select("""
//    SELECT bst.end_date FROM book_service_tb bst
//    WHERE bst.book_id = #{bookId} AND bst.service_id = #{serviceId};
//""")
//    LocalDateTime getEndDate(Integer bookId, Integer serviceId);
    @Select("""
    select bs.end_date from book_tb b
    join book_service_tb bs on  bs.book_id = b.book_id
    where bs.book_id = #{bookId};
""")
    LocalDateTime getEndDate( Integer bookId);

    @Select("""
    SELECT service_price FROM service_tb WHERE service_id = #{serviceId}
""")
    Double getServicePrice (Integer serviceId);

    @Select("""
    SELECT pt.discount_price
    FROM promotion_tb pt
    WHERE pt.promotion_id IN (
        SELECT spt.promotion_id
        FROM service_promotion_tb spt
        WHERE spt.service_id = #{serviceId}
    ) LIMIT 1 ;
""")
    Double getDisCountPrice(Integer serviceId);

    @Select("""
    SELECT service_id FROM book_service_tb WHERE  book_id=#{bookId};
""")
    Integer getServiceId(Integer bookId);
}
