package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.FeedbackOnShopRequest;
import com.example.springfinalproject.model.request.RateFeedbackRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RateRepository {
    @Select("""
            SELECT * FROM rate_feedback_tb
            """)
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "feedbackId", column = "feedback_id"),
                    @Result(property = "feedback", column = "feedback"),
                    @Result(property = "rate", column = "rate"),
                    @Result(property = "customerId", column = "user_id")
            }
    )
    List<RateFeedback> findAllRateFeedback();


    //view rating and feedback by order
    @Select("""
            SELECT
                    r.feedback_id,
                    profile_img,
                    username,
                    feedback,
                    o.order_id,
                    unit_price AS fullprice,
                    rate
            from rate_feedback_tb r
            INNER JOIN user_tb u on u.user_id = r.user_id
            INNER JOIN order_feedback_tb oft on r.feedback_id = oft.feedback_id
            INNER JOIN order_tb o on oft.order_id = o.order_id
            INNER JOIN order_product_tb opt on o.order_id = opt.order_id
            INNER JOIN product_tb p on p.product_id = opt.product_id
            INNER JOIN product_shop_tb ps on ps.product_id = opt.product_id
            where o.shop_id = #{shopId}
            """)
    @Result(property = "feedbackOrderId",column = "feedback_id")
    @Result(property = "profileImg", column = "profile_img")
    @Result(property = "orderId", column = "order_id")
    @Result(property = "customerName", column = "username")
    @Result(property = "feedback", column = "feedback")
    @Result(property = "fullprice", column = "fullprice")
    @Result(property = "rate", column = "rate")


    List<FeedbackOrder> getAllProductForDashboard(Integer shopId);

    //post rare feedback
    @Select("""
            INSERT INTO rate_feedback_tb (feedback, rate,user_id) VALUES (#{rq.feedback}, #{rq.rate},#{rq.userId}) RETURNING feedback_id
            """)
    Integer postRateFeedback(@Param("rq") RateFeedbackRequest rateFeedbackRequest);

    //post rare feedback
    @Select("""
    INSERT INTO rate_feedback_tb (feedback, rate,user_id,feedback_date) VALUES (#{rq.feedback}, #{rq.rate},#{rq.userId}, #{rq.dateTime})
    RETURNING feedback_id
    """)
    Integer postRateFeedbackBooking(@Param("rq") RateFeedbackRequest rateFeedbackRequest);

    @Select("""
            SELECT order_id from order_tb where order_id = #{orderId}
            """)
    Integer findOrderByIdForRate(Integer orderId);

    @Select("""
            INSERT INTO order_feedback_tb (feedback_id, order_id) VALUES (#{feedbackId},#{orderId})
            """)
    void saveOrderFeedback(Integer feedbackId, Integer orderId);

    //get rate and feedback by id
    @Select("""
    select * from order_tb rft
    INNER JOIN order_feedback_tb bft on rft.order_id = bft.order_id
    INNER JOIN rate_feedback_tb rf on bft.feedback_id = rf.feedback_id
    where bft.feedback_id = #{feedbackId} and shop_id=#{shopId};
    """)
    @ResultMap("mapper")
    RateFeedback getRateFeedbackById(Integer feedbackId, Integer shopId);

    //get All Rate Feedback For ServiceProvider
//    SELECT  o.feedback_id, profile_img, username,feedback,od.book_id, service_price as fullprice,rate
//    FROM rate_feedback_tb r
//    INNER JOIN user_tb u on r.user_id = u.user_id
//    INNER JOIN book_tb od on r.user_id = od.user_id
//    INNER JOIN book_feedback_tb o ON r.feedback_id = o.feedback_id
//    INNER JOIN book_service_tb op ON od.book_id = op.book_id
//    INNER JOIN service_tb p ON op.service_id = p.service_id
//    INNER JOIN service_shop_tb ps on p.service_id = ps.service_id
//    WHERE od.shop_id = #{shopId};
    @Select("""

            SELECT DISTINCT o.feedback_id,
                u.profile_img,
                u.username,
                feedback,
                od.book_id,
                service_price AS fullprice,
                r.rate
            FROM rate_feedback_tb r
            INNER JOIN user_tb u ON r.user_id = u.user_id
            INNER JOIN book_feedback_tb o ON r.feedback_id = o.feedback_id
            INNER JOIN book_tb od ON o.book_id = od.book_id
            INNER JOIN book_service_tb op ON od.book_id = op.book_id
            INNER JOIN service_tb p ON op.service_id = p.service_id
            INNER JOIN service_shop_tb ps ON p.service_id = ps.service_id
            WHERE od.shop_id = #{shopId};

            """)
    @Result(property = "feedbackBookId",column = "feedback_id")
    @Result(property = "profileImg", column = "profile_img")
    @Result(property = "bookId", column = "book_id")
    @Result(property = "customerName", column = "username")
    @Result(property = "feedback", column = "feedback")
    @Result(property = "fullprice", column = "fullprice")
    @Result(property = "rate", column = "rate")
    List<ReteFeedbackBook> getAllRateFeedbackForServiceProvider(Integer shopId);


    @Select("""
            select order_id from order_tb where order_id = #{orderId} and user_id = #{userId}
            """)
    Integer getOrderById(Integer orderId, Integer userId);

    @Select("""
    SELECT book_id from book_tb where book_id = #{bookId} and user_id = #{userId}
""")
    Integer getBookById(Integer bookId, Integer userId);

    //Get rate information by order id
    @Select("""
    SELECT * from rate_feedback_tb r
             Inner Join order_feedback_tb ort on r.feedback_id = ort.feedback_id
             Inner Join order_tb o on ort.order_id = o.order_id
        where o.order_id = #{orderId} and o.user_id = #{userId}
""")
    @Result( property = "feedbackId", column = "feedback_id")
    @Result( property = "customerId", column = "user_id")
    Integer getRateFeedbackByUserId(Integer orderId,Integer userId);

    //Get rate information by book id
    @Select("""
    SELECT * from rate_feedback_tb r
             Inner Join book_feedback_tb ort on r.feedback_id = ort.feedback_id
             Inner Join book_tb o on ort.book_id = o.book_id
        where o.book_id = #{bokIdGotFromDB} and o.user_id = #{userId}
""")
    @Result( property = "feedbackId", column = "feedback_id")
    @Result( property = "customerId", column = "user_id")
    Integer getRateFeedbackByUserIdForBooking(Integer bokIdGotFromDB, Integer userId);

    @Select("""
    INSERT INTO book_feedback_tb (feedback_id, book_id) VALUES (#{feedbackId},#{bookIdGotFromDB})
    """)
    void saveBookFeedback(Integer feedbackId, Integer bookIdGotFromDB);

    @Select("""
            select * from book_tb rft
            INNER JOIN book_feedback_tb bft on rft.book_id = bft.book_id
            INNER JOIN rate_feedback_tb rf on bft.feedback_id = rf.feedback_id
            where bft.feedback_id = #{feedbackId} and shop_id=#{shopId};
            """)
    @ResultMap("mapper")
    RateFeedback getRateFeedbackByIdOnBooking(Integer feedbackId, Integer shopId);


    //find all feedback by shop id
    @Select("""
    SELECT DISTINCT rft.feedback_id, rft.rate,profile_img, u.username, rft.feedback, feedback_date
    FROM rate_feedback_tb rft
    INNER JOIN user_tb u on rft.user_id = u.user_id
    INNER JOIN book_tb bt on rft.user_id = bt.user_id
    INNER JOIN book_feedback_tb bft ON rft.feedback_id = bft.feedback_id
    INNER JOIN book_service_tb bst ON bft.book_id = bst.book_id
    INNER JOIN service_tb sv ON bst.service_id = sv.service_id
    INNER JOIN service_shop_tb sst on sv.service_id = sst.service_id
    WHERE sst.shop_id = #{shopId}
    """)
    @Result( property = "feedbackId", column = "feedback_id")
    @Result(property = "profileImg", column = "profile_img")
    @Result( property = "customerId", column = "user_id")
    @Result(property = "feedbackDate", column = "feedback_date")
    List<FeedbackOnShopRequest> findAllFeedbackByShopId(Integer shopId);
}
