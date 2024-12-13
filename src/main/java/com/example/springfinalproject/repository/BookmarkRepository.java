package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.AllBookmarkRequest;
import com.example.springfinalproject.model.request.BookmarkEventRequest;
import com.example.springfinalproject.model.request.BookmarkRequest;
import com.example.springfinalproject.model.request.BookmarkServiceRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookmarkRepository {

    //Get All Bookmark in bookmark_tb
    @Select("""
    SELECT bt.bookmark_id,
           p.*,
           s.*,
           e.*
    FROM bookmark_tb bt
             LEFT JOIN product_bookmark_tb pbt ON pbt.bookmark_id = bt.bookmark_id
             LEFT JOIN product_tb p ON pbt.product_id = p.product_id
             LEFT JOIN serv_bookmark_tb sbt ON sbt.bookmark_id = bt.bookmark_id
             LEFT JOIN service_tb s ON sbt.service_id = s.service_id
             LEFT JOIN event_bookmark_tb ebt ON ebt.bookmark_id = bt.bookmark_id
             LEFT JOIN event_tb e ON ebt.event_id = e.event_id
    WHERE bt.user_id = #{userId}
""")
    @Results(id = "allBookmarkResultMap", value = {
            @Result(property = "bookmarkId", column = "bookmark_id"),
            @Result(property = "productDetails.productName", column = "product_name"),
            @Result(property = "productDetails.unitPrice", column = "unit_price"),
            @Result(property = "productDetails.productImg", column = "product_img"),
            @Result(property = "productDetails.productDescription", column = "product_description"),
            @Result(property = "productDetails.productQty", column = "product_qty"),
            @Result(property = "productDetails.expireDate", column = "expired_date"),
            @Result(property = "productDetails.isActive", column = "is_active"),
            @Result(property = "productDetails.barCode", column = "barcode"),
            @Result(property = "productDetails.category", column = "category_id",
                    one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById")
            ),
            @Result(property = "serviceDetails.serviceName", column = "service_name"),
            @Result(property = "serviceDetails.servicePrice", column = "service_price"),
            @Result(property = "serviceDetails.serviceImg", column = "service_image"),
            @Result(property = "serviceDetails.serviceDescription", column = "service_description"),
            @Result(property = "serviceDetails.category", column = "category_id",
                    one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById")
            ),
            @Result(property = "serviceDetails.isActive", column = "is_active"),
            @Result(property = "eventDetails.eventTitle", column = "event_title"),
            @Result(property = "eventDetails.eventDescription", column = "event_description"),
            @Result(property = "eventDetails.eventImg", column = "event_image"),
            @Result(property = "eventDetails.eventAddress", column = "event_address"),
            @Result(property = "eventDetails.startDate", column = "start_date"),
            @Result(property = "eventDetails.endDate", column = "end_date"),
            @Result(property = "eventDetails.shop", column = "shop_id",
                    one = @One(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")
            ),
    })
    List<AllBookmarkRequest> getAllBookmarks(@Param("userId") Integer userId);

    //Get all Product Bookmarks
    @Select("""
    SELECT pbt.bookmark_id,pt.*
FROM bookmark_tb b
INNER JOIN product_bookmark_tb pbt ON b.bookmark_id = pbt.bookmark_id
INNER JOIN user_tb u ON b.user_id = u.user_id
INNER JOIN product_tb pt ON pbt.product_id = pt.product_id
WHERE b.user_id = #{userId}
    """)
    @Results(
            id = "mapperProduct",
            value = {
                    @Result(property = "productId", column = "product_id"),
                    @Result(property = "bookmarkId",column = "bookmark_id"),
                    @Result(property = "productDetails.productName", column = "product_name"),
                    @Result(property = "productDetails.unitPrice", column = "unit_price"),
                    @Result(property = "productDetails.productImg", column = "product_img"),
                    @Result(property = "productDetails.productDescription", column = "product_description"),
                    @Result(property = "productDetails.productQty", column = "product_qty"),
                    @Result(property = "productDetails.expireDate", column = "expired_date"),
                    @Result(property = "productDetails.isActive", column = "is_active"),
                    @Result(property = "productDetails.barCode", column = "barcode"),
                    @Result(property = "productDetails.category", column = "category_id",
                        one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById")
                    ),
            }
    )
    List<AllBookmarkRequest> getAllBookmarkProducts(@Param("userId") Integer userId);
    //Get all Service Bookmarks
    @Select("""
    SELECT sbt.bookmark_id,pt.*
FROM bookmark_tb b
INNER JOIN serv_bookmark_tb sbt ON b.bookmark_id = sbt.bookmark_id
INNER JOIN user_tb u ON b.user_id = u.user_id
INNER JOIN service_tb pt ON sbt.service_id = pt.service_id
WHERE b.user_id = #{userId}
    """)
    @Results(
            id = "mapperService",
            value = {
                    @Result(property = "serviceId", column = "service_id"),
                    @Result(property = "bookmarkId",column = "bookmark_id"),
                    @Result(property = "serviceDetails.serviceName", column = "service_name"),
                    @Result(property = "serviceDetails.servicePrice", column = "service_price"),
                    @Result(property = "serviceDetails.serviceImg", column = "service_image"),
                    @Result(property = "serviceDetails.serviceDescription", column = "service_description"),
                    @Result(property = "serviceDetails.category", column = "category_id",
                            one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById")
                    ),
                    @Result(property = "serviceDetails.isActive", column = "is_active"),
            }
    )
    List<AllBookmarkRequest> getAllBookmarkServices(@Param("userId") Integer userId);
    //Get All Event Bookmarks

    @Select("""
    SELECT ebt.bookmark_id,et.*
FROM bookmark_tb b
INNER JOIN event_bookmark_tb ebt ON b.bookmark_id = ebt.bookmark_id
INNER JOIN user_tb u ON b.user_id = u.user_id
INNER JOIN event_tb et ON ebt.event_id = et.event_id
WHERE b.user_id = #{userId}
""")
    @Results(
            id = "mapperEvent",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "eventId", column = "event_id"),
                    @Result(property = "bookmarkId",column = "bookmark_id"),
                    @Result(property = "eventDetails.eventTitle", column = "event_title"),
                    @Result(property = "eventDetails.eventDescription", column = "event_description"),
                    @Result(property = "eventDetails.eventImg", column = "event_image"),
                    @Result(property = "eventDetails.eventAddress", column = "event_address"),
                    @Result(property = "eventDetails.startDate", column = "start_date"),
                    @Result(property = "eventDetails.endDate", column = "end_date"),
                    @Result(property = "eventDetails.shop", column = "shop_id",
                        one = @One(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")
                    ),
            }
    )
    List<AllBookmarkRequest> getAllBookmarkEvents(@Param("userId") Integer userId);

    //Add New Bookmark Product
    @Select("""
    INSERT INTO product_bookmark_tb(product_id, bookmark_id)
    VALUES ( #{product.productId}, #{bookmarkId})
    """)
    Integer insertProductBookmark(@Param("product") Product productId, @Param("bookmarkId") Integer bookmarkId);

    @Select("INSERT INTO bookmark_tb(user_id) VALUES (#{userId}) " +
            "RETURNING bookmark_id")
    Integer saveBookmark(@Param("userId") Integer userId);

    //Add New Bookmark Service
    @Select("""
    INSERT INTO serv_bookmark_tb(service_id, bookmark_id)
    VALUES ( #{service.serviceId}, #{bookmarkId})
    """)
    Integer insertServiceBookmark(@Param("service") ServiceApp serviceId, @Param("bookmarkId") Integer bookmarkId);

    //Add New Bookmark Event
    @Select("""
    INSERT INTO event_bookmark_tb(event_id, bookmark_id)
    VALUES ( #{event.eventId}, #{bookmarkId})
    """)
    Integer insertEventBookmark(@Param("event") Event eventId, @Param("bookmarkId") Integer bookmarkId);

    //Bookmark and Undo Bookmark Service
    @Select("""
    SELECT COUNT(*) > 0
    FROM serv_bookmark_tb sb
    JOIN bookmark_tb b ON sb.bookmark_id = b.bookmark_id
    WHERE sb.service_id = #{rq.serviceId} AND b.user_id = #{userId}
""")
    boolean isBookmarked(@Param("rq") BookmarkServiceRequest bookmarkServiceRequest, @Param("userId") Integer userId);

    @Delete("""
    DELETE FROM serv_bookmark_tb
    USING bookmark_tb
    WHERE serv_bookmark_tb.bookmark_id = bookmark_tb.bookmark_id
    AND serv_bookmark_tb.service_id = #{rq.serviceId}
    AND bookmark_tb.user_id = #{userId}
""")
    void deleteBookmark(@Param("rq") BookmarkServiceRequest bookmarkServiceRequest, @Param("userId") Integer userId);

    //Bookmark and Undo Bookmark Product
    @Select("""
    SELECT COUNT(*) > 0
    FROM product_bookmark_tb pbt
    JOIN bookmark_tb bt on pbt.bookmark_id = bt.bookmark_id
    WHERE pbt.product_id = #{rq.productId} AND bt.user_id = #{userId}
""")
    boolean isBookmarkedProduct(@Param("rq") BookmarkRequest bookmarkRequest, @Param("userId") Integer userId);

    @Delete("""
    DELETE FROM product_bookmark_tb
    USING bookmark_tb
    WHERE product_bookmark_tb.bookmark_id = bookmark_tb.bookmark_id
    AND product_bookmark_tb.product_id = #{rq.productId}
    AND bookmark_tb.user_id = #{userId}
""")
    void deleteBookmarks(@Param("rq") BookmarkRequest bookmarkRequest, @Param("userId") Integer userId);

    //Bookmark and Undo Bookmark Event
    @Select("""
    SELECT COUNT(*) > 0
    FROM event_bookmark_tb ebt 
    JOIN bookmark_tb bt on ebt.bookmark_id = bt.bookmark_id
    WHERE ebt.event_id = #{rq.eventId} AND bt.user_id = #{userId}
""")
    boolean isBookmarkedEvent(@Param("rq")BookmarkEventRequest bookmarkEventRequest, @Param("userId") Integer userId);

    @Delete("""
    DELETE FROM event_bookmark_tb
    USING bookmark_tb
    WHERE event_bookmark_tb.bookmark_id = bookmark_tb.bookmark_id
    AND event_bookmark_tb.event_id = #{rq.eventId}
    AND bookmark_tb.user_id = #{userId}
""")
    void deleteBookmarkEvent(@Param("rq")BookmarkEventRequest bookmarkEventRequest, @Param("userId") Integer userId);

    @Delete("""
    DELETE FROM bookmark_tb where bookmark_id = #{markId}
""")
    void deleteMark(Integer markId);
}
