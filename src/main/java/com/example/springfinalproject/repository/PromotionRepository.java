package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.Promotion;
import com.example.springfinalproject.model.dto.PromotionDto;
import com.example.springfinalproject.model.dto.PromotionTb;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.PromotionRequest;
import org.apache.ibatis.annotations.*;
import org.checkerframework.checker.index.qual.SameLen;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PromotionRepository {

    //get all promotion
    @Select("""
SELECT promotion_id, percentage, start_date, end_date, shop_id,promotion_title
,promotion_description, promotion_img,is_expired,discount_price FROM promotion_tb LIMIT #{size} OFFSET ${size} * (#{page}-1)
""")
    @Results( id = "promotionMapper",
            value =
            {
                    @Result(property = "promotionId",column = "promotion_id"),
                    @Result(property = "percentage",column = "percentage"),
                    @Result(property = "startDate",column = "start_date"),
                    @Result(property = "endDate",column = "end_date"),
                    @Result(property = "shopId", column = "shop_id",
                        one = @One(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")
                    ),
                    @Result(property = "promotiontitle",column = "promotion_title"),
                    @Result(property = "promotionDescription",column = "promotion_description"),
                    @Result(property = "promotionImage",column = "promotion_img"),
                    @Result(property = "isExpired",column = "is_expired"),
                    @Result(property = "discountPrice",column = "discount_price")
            }
    )

    List<PromotionTbWithId> getAllPromotion(Integer page, Integer size);

    //create promotion
    @Select("""
INSERT INTO promotion_tb(percentage,start_date,end_date,shop_id,promotion_title,promotion_description,promotion_img,is_expired,discount_price) 
VALUES (#{rq.percentage}, #{rq.startDate},#{rq.endDate},#{rq.shopId},#{rq.promotiontitle},
 #{rq.promotionDescription},#{rq.promotionImage},#{rq.isExpired},#{rq.discountPrice}) RETURNING *;
""")
    @Results(
            id = "mapperPromotionTbWithId",
            value = {
                    @Result(property = "promotionId",column = "promotion_id"),
                    @Result(property = "percentage",column = "percentage"),
                    @Result(property = "startDate",column = "start_date"),
                    @Result(property = "endDate",column = "end_date"),
                    @Result(property = "shopId", column = "shop_id",
                            one = @One(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")
                    ),
                    @Result(property = "promotiontitle",column = "promotion_title"),
                    @Result(property = "promotionDescription",column = "promotion_description"),
                    @Result(property = "promotionImage",column = "promotion_img"),
                    @Result(property = "isExpired",column = "is_expired"),
                    @Result(property = "discountPrice",column = "discount_price")
            }
    )
    PromotionTbWithId saveToPromotionTb(@Param("rq") PromotionTb promotionTb);

@Insert("""
INSERT INTO product_promotion_tb(promotion_id,product_id) values (#{promotionId},#{productId})
""")
    void savePromotionProduct(Integer promotionId, Integer productId);

    //get product id
    @Select("""
select * from promotion_tb where promotion_id = #{promotionId}
""")
    @Results(
            id = "map",
            value = {
                    @Result(property = "promotionId",column = "promotion_id"),
                    @Result(property = "promotionTitle",column = "promotion_title"),
                    @Result(property = "promotionDescription",column = "promotion_description"),
                    @Result(property = "percentage",column = "percentage"),
                    @Result(property = "startDate",column = "start_date"),
                    @Result(property = "endDate",column = "end_date"),
                    @Result(property = "productList",column = "promotion_id",
                    many = @Many(select = "getProductByPromotionId")
                    ),
                    @Result(property = "isExpired",column = "is_expired")
            }
    )
    PromotionDto getPromotionById(Integer promotionId);

    //get product by promotion id
    @Select("""

            SELECT p.* from product_tb p
                INNER JOIN product_promotion_tb pp on p.product_id = pp.product_id
                where promotion_id=#{promotionId}
""")
    @Results(
            id = "productMapper",
            value = {
                    @Result(property = "productId",column = "product_id"),
                    @Result(property = "productName",column = "product_name"),
                    @Result(property = "unitPrice",column = "unit_price"),
                    @Result(property = "productDate",column = "product_date"),
                    @Result(property = "productDescription",column = "product_description"),
                    @Result(property = "productQty",column = "product_qty"),
                    @Result(property = "productImg",column = "product_img"),
                    @Result(property = "expireDate",column = "expired_date"),
                    @Result(property = "isActive",column = "is_action"),
                    @Result(property = "category",column = "category_id",
                            one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById"))
            }
    )
    List<Product> getProductByPromotionId(Integer promotionId);

    //delete promotion
    @Delete("""
DELETE from product_promotion_tb where promotion_id= #{promotionId}
""")
    boolean deletePromotionProduct(Integer promotionId);

    //update promotion
    @Update("""
UPDATE promotion_tb SET promotion_title = #{rq.promotionTitle},promotion_description = #{rq.promotionDescription} , percentage = #{rq.percentage}, start_date = #{rq.startDate}, end_date = #{rq.endDate} where promotion_id = #{promotionId}
""")
    void updatePromotionById(@Param("rq") PromotionRequest promotionRequest, Integer promotionId);


    //get promotion by shop id
    @Select("""
    SELECT * FROM promotion_tb WHERE shop_id = #{shopId} LIMIT #{size} OFFSET #{size}*(#{page}-1)
    """)
    @ResultMap("mapperPromotionTbWithId")
    List<PromotionTbWithId> getPromotionShopId(Integer shopId, Integer page, Integer size);

    //Get oldest promotion
    @Select("""
    SELECT *
    FROM promotion_tb
    WHERE shop_id = #{shopId}
    ORDER BY start_date ASC
    LIMIT 10;
""")
    @ResultMap("mapperPromotionTbWithId")
    List<PromotionTbWithId> getOldestPromotion( Integer shopId);


    @Select("""
    SELECT *
    FROM promotion_tb
    WHERE start_date <= CURRENT_DATE
      AND end_date >= CURRENT_DATE
      AND DATE_PART('week', start_date) = DATE_PART('week', CURRENT_DATE)
      AND DATE_PART('year', start_date) = DATE_PART('year', CURRENT_DATE)
      AND shop_id = #{shopId}
""")
    @ResultMap("mapperPromotionTbWithId")
    List<PromotionTbWithId> getThisWeekPromotion(Integer shopId);


    @Select("""
    DELETE  FROM promotion_tb WHERE promotion_id= #{promotionId} RETURNING *;
""")
    @ResultMap("mapperPromotionTbWithId")
    PromotionTbWithId deletePromotionWithId( Integer promotionId);
    @Select("""
    INSERT INTO service_promotion_tb VALUES(default,#{serviceId},#{promotionId});
""")
    void saveToServicePromotionTb(Integer serviceId,Integer promotionId);

    @Select(""" 
   SELECT pt.percentage
   FROM promotion_tb pt
   INNER JOIN product_promotion_tb ppt ON pt.promotion_id = ppt.promotion_id
   WHERE ppt.product_id = #{productId}
    ORDER BY pt.is_expired desc LIMIT 1 ;
""")
    Double productPercentage(Integer productId);
    @Select("""
    SELECT p.promotion_title
     FROM promotion_tb p
    JOIN service_promotion_tb sp ON p.promotion_id = sp.promotion_id
    WHERE service_id = #{serviceId};
""")
    String getPromotionTitle(Integer serviceId);

}
