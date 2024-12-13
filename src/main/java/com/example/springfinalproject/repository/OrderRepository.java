package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Order;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.*;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderRepository {

    @Select("""
SELECT DISTINCT
    o.order_id,
    o.order_address,
    u.username,
    u.profile_img,
    st.status_type,
    s.shop_name,
    SUM(
            CASE
                WHEN pp.promotion_id IS NOT NULL AND #{now} BETWEEN p_promotion.start_date AND p_promotion.end_date  AND p_promotion.is_expired = false THEN
                    p.unit_price * (1 - p_promotion.percentage /100) * opt.qty
                ELSE
                    p.unit_price * opt.qty
                END
    ) AS total_amount
FROM order_product_tb opt
         INNER JOIN order_tb o ON opt.order_id = o.order_id
         INNER JOIN user_tb u ON o.user_id = u.user_id
         INNER JOIN shop_tb s ON s.user_id = u.user_id
         INNER JOIN status_tb st ON o.status_id = st.status_id
         INNER JOIN product_tb p ON opt.product_id = p.product_id
         LEFT JOIN product_promotion_tb pp ON p.product_id = pp.product_id
         LEFT JOIN promotion_tb p_promotion ON pp.promotion_id = p_promotion.promotion_id
WHERE o.shop_id = #{shopId} AND status_type = 'Waiting'
GROUP BY o.order_id, o.order_address, u.username, u.profile_img, st.status_type, s.shop_name;
""")
    @Results(
            id = "orderMapper",
            value = {
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderAddress", column = "order_address"),
            @Result(property = "user", column = "username"),
            @Result(property = "profileImg", column = "profile_img"),
            @Result(property = "status", column = "status_type"),
            @Result(property = "productList", column = "order_id",
                    many = @Many(select = "getProductByOrderId")),
            @Result(property = "shopName", column = "shop_name"),
            @Result(property = "TotalAmount", column = "total_amount"),
    })
    List<Order> findAllOrder(LocalDateTime now,Integer shopId);

//find order by id
    @Select("""

            SELECT DISTINCT
            o.order_id,
            o.order_address,
            u.username,
            u.profile_img,
            st.status_type,
            s.shop_name,
            SUM(
                    CASE
                        WHEN pp.promotion_id IS NOT NULL AND o.order_date BETWEEN p_promotion.start_date AND p_promotion.end_date  AND p_promotion.is_expired = false THEN
                    p.unit_price * (1 - p_promotion.percentage /100) * opt.qty
                    ELSE
                    p.unit_price * opt.qty
                    END
            ) AS total_amount
        FROM order_product_tb opt
                 INNER JOIN order_tb o ON opt.order_id = o.order_id
                 INNER JOIN user_tb u ON o.user_id = u.user_id
                 INNER JOIN shop_tb s ON o.shop_id = s.shop_id
                 INNER JOIN status_tb st ON o.status_id = st.status_id
                 INNER JOIN product_tb p ON opt.product_id = p.product_id
                 LEFT JOIN product_promotion_tb pp ON p.product_id = pp.product_id
                 LEFT JOIN promotion_tb p_promotion ON pp.promotion_id = p_promotion.promotion_id
        WHERE o.order_id = #{orderId}
        GROUP BY o.order_id, o.order_address, u.username, u.profile_img, st.status_type
               , s.shop_name
    """)
    @ResultMap("orderMapper")
//    List<MappingHelper<Integer, ProductWithQtyRequest>> findOrderById(Integer id);
    Order findOrderById(Integer orderId);

    //get product by id
    @Select("""
    select * from product_tb where product_id = #{productId}
    """)
    @Results(
            id = "productMap",
            value = {
                    @Result(property = "productId",column = "product_id"),
                    @Result(property = "productName",column = "product_name"),
                    @Result(property = "unitPrice",column = "unit_price"),
                    @Result(property = "productDate",column = "product_date"),
                    @Result(property = "productDescription",column = "product_description"),
                    @Result(property = "productQty",column = "product_qty"),
                    @Result(property = "productImg",column = "product_img"),
                    @Result(property = "expireDate",column = "expired_date"),
                    @Result(property = "isActive",column = "is_active"),
                    @Result(property = "category",column = "category_id",
                            one = @One(select = "com.example.springfinalproject.repository.CategoryRepository.getCategoryById"))
            }
    )
    Product getProductById(Integer id);

    //find qty by order id in product table
    @Select("""
      select p.product_qty
      from product_tb p
      inner join order_product_tb opt on p.product_id = opt.product_id
      where opt.order_id = #{id}
""") //kom plex check shop id pg

//    @Results({
//            @Result(property = "productName", column = "product_name"),
//            @Result(property = "qty", column = "qty"),
//    })
    List<Integer> getProductTableByOrderId(Integer id);

    //find qty by order in order product id
    @Select("""
    select  opt.qty
    from product_tb p
    inner join order_product_tb opt on p.product_id = opt.product_id
    where opt.order_id = #{id};
    """)
    List<Integer> getQtyByOrderId(Integer id);


    //Select product that have order to show in list
    @Select("""
    select p.product_id,p.product_img,p.unit_price, p.product_name, opt.qty
    from order_product_tb opt
    inner join product_tb p on opt.product_id = p.product_id
    where order_id =  #{id}
    """)
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productImg", column = "product_img"),
            @Result(property = "unitPrice", column = "unit_price"),
            @Result(property = "productName", column = "product_name")
    })
    List<ProductWithQtyRequest> getProductByOrderId(Integer id);


    //Insert order Table
    @Select("INSERT INTO order_tb(order_address, order_date, status_id, shop_id, user_id) " +
            "VALUES (#{rq.orderAddress}, #{now}, 1, #{rq.shop}, #{rq.user}) " +
            "RETURNING order_id")
    Integer insertOrder(@Param("rq") OrderProductRequest orderProductRequest, LocalDateTime now);

    //Insert Order product table
    @Select("INSERT INTO order_product_tb(order_id, product_id,qty) " +
            "VALUES(#{orderId}, #{productId}, #{qty})")
    void insertOrderProduct(Integer orderId, Integer productId, Integer qty);




    @Select("SELECT product_id FROM product_tb WHERE product_id = #{productId}")
    Integer findProductById(Integer productId);


    //get shop from product id to insert in order_tb
    @Select("""
    select ps.shop_id
    from product_tb p
    inner join product_shop_tb ps on p.product_id = ps.product_id
    where ps.product_id = #{productId}
""")

    Integer getShopIdInProduct(Integer productId);


    @Select("""
    INSERT INTO order_notification_tb(order_id, notification_id)
    VALUES (#{orderId}, #{notificationId})
""")
    void insertOrderNotification(Integer orderId,Integer notificationId);


    @Select("""
    INSERT INTO notification_tb(notification_type, notification_message, notification_date, isread,reciever_id,sender_id)
    VALUES ('order' , #{notificationMessage}, #{now} , false, #{shopId}, #{senderId})
    RETURNING notification_id
    """)
    Integer insertNotification(LocalDateTime now,String notificationMessage,Integer shopId,Integer senderId);

    @Select("""
    SELECT username FROM user_tb WHERE user_id = #{userId}
""")
    String getUsernameByUserId(Integer userId);

    @Select("""

    UPDATE order_tb SET status_id = 2 WHERE shop_id = #{shopId} AND order_id = #{orderId}
    RETURNING order_id
    
""")
    Integer updateOrderStatus(Integer orderId,Integer shopId);

    @Select("""
   SELECT DISTINCT on (o.order_id)
       o.order_id,
       o.order_address,
       u.username,
       u.profile_img,
       st.status_type,
       s.shop_name
   FROM order_product_tb
            opt
            INNER JOIN order_tb o ON opt.order_id = o.
            order_id
            INNER JOIN user_tb u ON o.user_id = u
            .user_id
            INNER JOIN shop_tb s ON o.user_id = u.user_id
            INNER JOIN status_tb st ON o.status_id =
            st.status_id
            INNER JOIN product_tb p ON opt.product_id = p.product_id
            LEFT JOIN product_promotion_tb pp ON p.product_id
            = pp.product_id
            LEFT JOIN promotion_tb p_promotion ON pp.promotion_id =
            p_promotion.promotion_id
   WHERE opt.order_id = #{orderId} and o.shop_id = #{shopId}
   GROUP BY o.order_id, o.order_address, u.username, u.profile_img, st.status_type, s.shop_name
    """)
    Integer findOrderByShopId(Integer orderId,Integer shopId);


    //Seller deny endpoint
    @Select("""
    UPDATE order_tb SET status_id = 3 WHERE shop_id = #{shopId} AND order_id = #{orderId}
    RETURNING order_id
    """)
    Integer updateOrderStatusDeny(Integer orderId,Integer shopId);


    //seller delivery to done
    @Select("""
    UPDATE order_tb SET status_id = 4 WHERE shop_id = #{shopId} AND order_id = #{id}
    RETURNING order_id
""")
    Integer updateOrderStatusDelivery(Integer id, Integer shopId);


    @Select("""
    UPDATE order_tb SET status_id = 5 WHERE shop_id = #{shopId} AND order_id = #{id}
    RETURNING order_id
""")
    Integer updateOrderStatusDone(Integer id, Integer shopId);


    //get status id by orderId
    @Select("""
    select status_id from order_tb where order_id=#{orderId}
""")
    Integer getStatusIdByOrderId(Integer orderId);


    //get all history order

    @Select("""
    SELECT
        u.username,
        u.email,
        u.profile_img,
        o.order_id,
        o.order_date,
        st.status_type,
    SUM(
            CASE
                WHEN pp.promotion_id IS NOT NULL AND p_promotion.is_expired = false THEN
                    p.unit_price * (1 - p_promotion.percentage /100) * op.qty
                ELSE
                    p.unit_price * op.qty
                END
    ) AS total_amount
    FROM
        order_tb o
            INNER JOIN
        user_tb u ON o.user_id = u.user_id
            INNER JOIN
        order_product_tb op ON op.order_id = o.order_id
            INNER JOIN
        product_tb p ON op.product_id = p.product_id
        INNER JOIN status_tb st ON o.status_id = st.status_id
        LEFT JOIN product_promotion_tb pp ON p.product_id = pp.product_id
        LEFT JOIN promotion_tb p_promotion ON pp.promotion_id = p_promotion.promotion_id

    WHERE o.shop_id = #{shopId}
    GROUP BY
        u.username,
        u.email,
        u.profile_img,
        o.order_id,
        o.order_date,
        st.status_type;
""")
    @Results(
            id = "historyMapper",
            value = {
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "image", column = "profile_img"),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderDate", column = "order_date"),
            @Result(property = "status", column = "status_type"),
            @Result(property = "totalAmount", column = "total_amount"),
    })
    List<OrderHistoryRequest> findAllOrderHistory(Integer shopId);


    @Select("""
    select distinct on (o.order_id) u.profile_img, u.username, o.order_date, opt.order_id, s.shop_name, o.order_address, u.email
    from order_tb o
     inner join order_product_tb opt on opt.order_id = o.order_id
     inner join user_tb u on o.user_id = u.user_id
     inner join shop_tb s on o.user_id = u.user_id
     inner join product_shop_tb ps on ps.shop_id = s.shop_id
    where opt.order_id = #{id};
    """)
    @Result(property = "profileImg", column = "profile_img")
    @Result(property = "username" , column = "username")
    @Result(property = "orderDate" , column = "order_date")
    @Result(property = "orderId", column = "order_id")
    @Result(property = "shopName", column = "shop_name")
    @Result(property = "shopAddress", column = "order_address")
    @Result(property = "email", column = "email")
    InvoiceRequest findOrderByOrderId(Integer id);

    @Select("""
    SELECT p.product_id from product_tb p
    inner join product_shop_tb ps on p.product_id = ps.product_id
    where  shop_id = #{shopId}
    """)
    List<Integer> getProductByShopId(Shop shopId);


    //Get all order history customer
    @Select("""
    SELECT
        u.username,
        u.email,
        p.product_img,
        o.order_id,
        o.order_date,
        st.status_type,
    SUM(
            CASE
                WHEN pp.promotion_id IS NOT NULL AND p_promotion.is_expired = false THEN
                    p.unit_price * (1 - p_promotion.percentage /100) * op.qty
                ELSE
                    p.unit_price * op.qty
                END
    ) AS total_amount
    FROM
        order_tb o
            INNER JOIN
        user_tb u ON o.user_id = u.user_id
            INNER JOIN
        order_product_tb op ON op.order_id = o.order_id
            INNER JOIN
        product_tb p ON op.product_id = p.product_id
        INNER JOIN status_tb st ON o.status_id = st.status_id
        LEFT JOIN product_promotion_tb pp ON p.product_id = pp.product_id
        LEFT JOIN promotion_tb p_promotion ON pp.promotion_id = p_promotion.promotion_id

    WHERE o.user_id = #{userId} AND o.status_id = 1 OR o.status_id = 2 OR o.status_id = 3 OR o.status_id = 4
    GROUP BY
        u.username,
        u.email,
        p.product_img,
        o.order_id,
        o.order_date,
        st.status_type;
    """)
    @Results( {
                    @Result(property = "username",column = "username"),
                    @Result(property = "email",column = "email"),
                    @Result(property = "image",column = "product_img"),
                    @Result(property = "orderId",column = "order_id"),
                    @Result(property = "orderDate",column = "order_date"),
                    @Result(property = "status",column = "status_type"),
                    @Result(property = "totalAmount",column = "total_amount"),
            }
    )
    List<OrderHistoryRequest> findAllOrderHistoryCustomer(Integer userId);


    //Order in customer history that have status done
    @Select("""
        SELECT
        u.username,
        u.email,
        p.product_img,
        o.order_id ,
        o.order_date,
        st.status_type,
        sh.shop_type_id,
        SUM(
                CASE
                    WHEN pp.promotion_id IS NOT NULL AND p_promotion.is_expired = false THEN
                        p.unit_price * (1 - p_promotion.percentage /100) * op.qty
                    ELSE
                        p.unit_price * op.qty
                    END
        ) AS total_amount
    FROM
        order_tb o
            INNER JOIN user_tb u ON o.user_id = u.user_id
            INNER JOIN order_product_tb op ON op.order_id = o.order_id
            INNER JOIN product_tb p ON op.product_id = p.product_id
            INNER JOIN status_tb st ON o.status_id = st.status_id
            INNER JOIN shop_tb sh ON o.shop_id = sh.shop_id
            LEFT JOIN product_promotion_tb pp ON p.product_id = pp.product_id
            LEFT JOIN promotion_tb p_promotion ON pp.promotion_id = p_promotion.promotion_id
    WHERE
        o.user_id = #{userId} AND o.status_id = 5
    GROUP BY
        u.username,
        u.email,
        p.product_img,
        o.order_id,
        o.order_date,
        st.status_type,
        sh.shop_type_id
    """)
   @Results({
       @Result(property = "username", column = "username"),
       @Result(property = "email", column = "email"),
       @Result(property = "productImg", column = "product_img"),
       @Result(property = "orderId", column = "order_id"),
       @Result(property = "orderDate", column = "order_date"),
       @Result(property = "status", column = "status_type"),
       @Result(property = "shopTypeId", column = "shop_type_id"),
       @Result(property = "totalAmount", column = "total_amount"),
   })
    List<OrderBookHistoryRequest> findAllOrderAndBookHistory(Integer userId);


    //Cancel order by id
    @Select("""
    UPDATE order_tb SET status_id = 6 WHERE order_id = #{orderId}
    RETURNING order_id
    """)
    Integer cancelOrder(Integer orderId);
}
