package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.*;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ShopRepository {

    @Select("SELECT ST_X(location) AS lat, ST_Y(location) AS longitude, * FROM shop_tb WHERE is_active = true " +
            "LIMIT #{size} OFFSET #{size} * (#{page}-1)")
    @Results(id = "mapper",
            value = {
                @Result(property = "shopId" ,column = "shop_id"),
                @Result(property = "shopName", column = "shop_name"),
                    @Result(property = "startDate", column = "start_date"),
                    @Result(property = "endDate", column = "end_date"),
                    @Result(property = "shopProfileImg", column = "shop_profile_img"),
                    @Result(property = "shopProfileCover", column = "shop_cover_img"),
                    @Result(property = "lat", column = "lat"),
                    @Result(property = "longitude", column = "longitude"),
                    @Result(property = "phoneNumber", column = "phone_number"),
                    @Result(property = "description", column = "description"),
                    @Result(property = "isActive", column = "is_active"),
                    @Result(property = "isAvailable", column = "is_available"),
                    @Result(property = "user", column = "user_id",
                        one = @One(select = "com.example.springfinalproject.repository.AppUserRepository.findUserById")
                    ),
                    @Result(property = "shopType", column = "shop_type_id",
                        many = @Many(select = "com.example.springfinalproject.repository.ShopTypeRepository.findShopTypeById")
                    ),
                    @Result(property = "email", column = "email")
            }
    )
    List<Shop> findAllShop( Integer page,  Integer size);

    //find shop by shop id
    @Select("SELECT ST_X(location) AS lat, ST_Y(location) AS longitude, * FROM shop_tb WHERE shop_id = #{id}")
    @ResultMap("mapper")
    Shop findShopById(Integer id);


    //Find user id in shop
    @Select("SELECT shop_id FROM shop_tb WHERE user_id = #{userId}")
    Integer findShopByUserId(Integer userId);

    //Create shop

    @Select("INSERT INTO shop_tb(shop_name, shop_type_id, email, shop_profile_img, location, phone_number, user_id, is_active) " +
            "VALUES (#{rq.shopName},#{rq.shopTypeId}, #{rq.email},#{rq.shopProfileImg}, point(#{rq.lat}::float8, #{rq.longti}::float8)::geometry, #{rq.phoneNumber}, #{rq.userId}, #{rq.isActive})" +
            "RETURNING shop_id")

//    @ResultMap("mapper")

    Integer insertNewShop(@Param("rq") ShopCreateRequest shopCreateRequest);


    //update shop info
    @Select("UPDATE shop_tb SET shop_name = #{rq.shopName}, email = #{rq.email}, phone_number = #{rq.phoneNumber} " +
            "WHERE shop_id = #{rq.shopId} " +
            "RETURNING shop_id")
//    @ResultMap("mapper")
    Integer editShop(@Param("rq") ShopUpdateRequest shopUpdateRequest);


    @Delete("UPDATE shop_tb SET is_active = false " +
            "WHERE shop_id = #{shopId} " +
            "RETURNING shop_id")
    boolean deleteShop(Integer shopId);

    @Select("SELECT * FROM shop_tb s WHERE LOWER(s.shop_name) LIKE LOWER(CONCAT('%', #{name}, '%'))")
    @ResultMap("mapper")
    List<Shop> findShopByName(String name);

    //deactivate shop
    @Select("UPDATE shop_tb SET description = #{rq.description} " +
            "WHERE shop_id = #{rq.shopId} " +
            "RETURNING shop_id")
    Integer editShopDescription(@Param("rq") ShopUpdateDescriptionRequest shopUpdateDescriptionRequest);

    //activate shop
    @Update("UPDATE shop_tb SET is_active = true " +
            "WHERE shop_id = #{shopId} ")
    boolean activeShop(Integer shopId);

    @Select("""
    UPDATE shop_tb SET start_date = #{rq.startDate}, end_date = #{rq.endDate}
    WHERE shop_id = #{rq.shopId}
    RETURNING shop_id;
""")
    Integer insertShopDayOff(@Param("rq") ShopDayOffRequest shopDayOffRequest);


    @Select(" UPDATE shop_tb SET shop_profile_img = #{rq.shopProfileImg} " +
            " WHERE shop_id = #{rq.shopId} " +
            " RETURNING shop_id")
    Integer insertShopImg(@Param("rq") ShopProfileRequest shopProfileRequest);


    @Select("UPDATE shop_tb SET location = #{rq.location} " +
            "WHERE shop_id = #{rq.shopId} " +
            "RETURNING shop_id")
    Integer editShopLocation(@Param("rq") ShopLocationRequest shopLocationRequest);


    @Select("SELECT is_active FROM shop_tb WHERE shop_id = #{shopId}")
    boolean shopStatus(Integer shopId);


    //Get all shop by current user
    @Select("""
        SELECT ST_X(location) AS lat, ST_Y(location) AS longitude,* FROM shop_tb WHERE user_id = #{userId}
""")
    @ResultMap("mapper")
    Shop findShopByCurrentUser(Integer userId);

    @Update("""
        UPDATE shop_tb
        SET is_available = CASE
                            WHEN #{now} NOT BETWEEN start_date AND end_date THEN true
                            ELSE false
                            END;
""")
    void updateShopNotAvailable(LocalDateTime now);


    //UPDATE SHOP COVER IMGAGE
    @Select("""
    UPDATE shop_tb SET shop_cover_img = #{rq.shopProfileCover}
    WHERE shop_id = #{rq.shopId}
    RETURNING shop_id;
""")
    Integer updateShopCoverImg(@Param("rq") ShopCoverImageRequest shopCoverImageRequest);




    //Find total action in shop
    @Select("""
    select count(order_id)
    from order_tb
    where shop_id = #{shopId};
    """)
    Integer findTotalSale(Integer shopId);

    //find total sale
    @Select("""
    select count(product_id)
    from product_shop_tb
    where shop_id = #{shopId};
    """)
    Integer findTotalProduct(Integer shopId);

    //find total customer
    @Select("""
    select count(distinct user_id)
    from order_tb
    where shop_id = #{shopID};
    """)
    Integer findTotalCustomer(Integer shopId);


    //find feed back on dashboard seller
    @Select("""
    select feedback_id, u.profile_img, u.username, p.product_name, p.unit_price
    from rate_feedback_tb r
    inner join user_tb u on r.user_id = u.user_id
    inner join shop_tb s on s.user_id = u.user_id
    inner join product_shop_tb ps on ps.shop_id = s.shop_id
    inner join product_tb p on p.product_id = ps.product_id
    """)
    @Results({
            @Result(property = "feedbackId", column = "feedback_id"),
            @Result(property = "profileImg", column = "profile_img"),
            @Result(property = "username", column = "username"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "unitPrice", column = "unit_price")
    })
    List<ShopFeedbackRequest> findFeedbackSeller();


    //get all order that customer have order
    @Select("""
    select * from order_tb where shop_id = #{shopId} and status_id = 1
""")
    @Results(
            id = "orderMap",
            value = {
                    @Result(property = "orderId", column = "order_id"),
                    @Result(property = "orderAddress", column = "order_address"),
                    @Result(property = "orderDate", column = "order_date"),
                    @Result(property = "user", column = "user_id",
                            one = @One(select = "com.example.springfinalproject.repository.AppUserRepository.findUserById")
                    ),
                    @Result(property = "status", column = "status_id",
                            one = @One(select = "com.example.springfinalproject.repository.StatusRepository.findStatusById")
                    ),
                    @Result(property = "shop" , column = "shop_id",
                            many = @Many(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")
                    )
            }
    )
    List<Order> findAllOrders(Integer shopId);


    //get all popular shops
    @Select("""
SELECT shop_id,AVG(rate) as shopRating from order_tb o
INNER JOIN order_feedback_tb of on o.order_id = of.order_id
Inner Join rate_feedback_tb rf on of.feedback_id = rf.feedback_id
group by shop_id;
""")
    @Result(property = "shopRating",column = "shopRating")
    @Result(property = "shop",column = "shop_id",
    one = @One(select = "findShopById"))
    List<PopularShop> getPopularShopByRate();


    @Select("""
SELECT shop_id, AVG(rate) as shopRating from book_tb o
INNER JOIN book_feedback_tb of on o.book_id = of.book_id
Inner Join rate_feedback_tb rf on of.feedback_id = rf.feedback_id
group by shop_id;
""")
    @Result(property = "shopRating",column = "shopRating")
    @Result(property = "shop",column = "shop_id",
            one = @One(select = "findShopById"))
    List<PopularShop> getPopularServiceShopByRate();

    //Get shop nearby user
    @Select("""
    SELECT ST_X(location) AS lat, ST_Y(location) AS longitude, * , ST_DistanceSphere(ST_GeomFromText(#{geometry}, 4326), location) / 1000 AS distance
    FROM shop_tb
    WHERE ST_DistanceSphere(ST_GeomFromText(#{geometry}, 4326), location) / 1000 < 15
    ORDER BY distance;
    """)
    @ResultMap("mapper")
    List<Shop> findNearbyShops(@Param("geometry") String geometry);


    //get get Rating Product Shop By ShopId
    @Select("""
SELECT AVG(rate) as shopRating from order_tb o
Inner Join order_feedback_tb of on o.order_id = of.order_id
INNER JOIN rate_feedback_tb rf on of.feedback_id = rf.feedback_id
where shop_id = #{shopId};
""")
    @Result(property = "shopRating",column = "shopRating")
    PopularShop getRatingProductShopByShopId(Integer shopId);

    //get Rating service Shop By ShopId
    @Select("""
SELECT AVG(rate) as shopRating from book_tb b
INNER JOIN book_feedback_tb bf on b.book_id = bf.book_id
INNER JOIN rate_feedback_tb rf on bf.feedback_id = rf.feedback_id
where shop_id =#{shopId};
""")
    @Result(property = "shopRating",column = "shopRating")
    PopularShop getRatingServiceShopByShopId(Integer shopId);

    //get Overview Service Provider
    @Select("""
            SELECT count(distinct user_id) from book_tb
            where shop_id = #{shopId}
            """)
    Integer getCustomer(Integer shopId);

    @Select("""
            SELECT count(DISTINCT book_id) from book_tb
where shop_id = #{shopId}
            """)
    Integer getAppointment(Integer shopId);
@Select("""
        SELECT count(DISTINCT service_id) from service_shop_tb
        where shop_id = #{shopId}
        """)
    Integer getService(Integer shopId);

@Select("""
        SELECT count(DISTINCT bft.feedback_id) from rate_feedback_tb rft
              inner join book_feedback_tb bft on rft.feedback_id = bft.feedback_id
              INNER JOIN book_tb b on shop_id = shop_id
              where shop_id = #{shopId}
        """)
    Integer getFeedback(Integer shopId);



    @Select("""
    SELECT shop_name from shop_tb WHERE user_id = #{userId};
""")
    String getShopNameWithUserId(Integer userId);

    @Select("""
    SELECT shop_id FROM shop_tb WHERE user_id =#{userId};
""")
    Integer getShopId(Integer userId);


    //get customer request
    @Select("""
    select count(distinct opt.order_id)
    from order_product_tb opt
    INNER JOIN order_tb o on opt.order_id = o.order_id
    where shop_id = #{shopId}
    """)
    Integer findTotalCustomerRequest(Integer shopId);
}
