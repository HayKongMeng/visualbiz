package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Book;
import com.example.springfinalproject.model.Entity.BookSeller;
import com.example.springfinalproject.model.request.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookRepository {

    @Select("""
SELECT DISTINCT
    b.book_id,
    u.username,
    u.profile_img,
    st.status_type,
    s.shop_name,
    bst.start_date,
    bst.end_date,
    SUM(
            CASE
                WHEN spt.promotion_id IS NOT NULL AND bst.start_date BETWEEN p_promotion.start_date AND p_promotion.end_date  AND p_promotion.is_expired = false THEN
            sv.service_price * (1 - p_promotion.percentage /100)
            ELSE
            sv.service_price
            END
    ) AS total_amount
    FROM book_service_tb bst
         INNER JOIN book_tb b ON bst.book_id = b.book_id
         INNER JOIN user_tb u ON b.user_id = u.user_id
         INNER JOIN shop_tb s ON s.user_id = u.user_id
         INNER JOIN status_tb st ON b.status_id = st.status_id
         INNER JOIN service_tb sv ON bst.service_id = sv.service_id
         LEFT JOIN service_promotion_tb spt ON sv.service_id = spt.service_id
         LEFT JOIN promotion_tb p_promotion ON spt.promotion_id = p_promotion.promotion_id
    WHERE b.user_id = #{userId}
    GROUP BY b.book_id, u.username, u.profile_img, st.status_type, s.shop_name,bst.start_date,
             bst.end_date
    """)
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "bookId", column = "book_id"),
                    @Result(property = "user", column = "username"),
                    @Result(property = "profileImg", column = "profile_img"),
                    @Result(property = "status", column = "status_type"),
                    @Result(property = "serviceList", column = "book_id",
                        many = @Many(select = "serviceList")
                    ),
                    @Result(property = "shopName", column = "shop_name"),
                    @Result(property = "amount", column = "total_amount")
            }
    )
    List<Book> getAllBook(Integer userId,Integer page, Integer size);


    //get service list
    @Select("""
    select bst.service_id, s.service_name, bst.start_date, bst.end_date
    from book_service_tb bst
    inner join book_tb b on bst.book_id = b.book_id
    inner join service_tb s on bst.service_id = s.service_id
    where bst.book_id = #{bookId}
    """)
    @Results({
            @Result(property = "serviceId", column = "service_id"),
            @Result(property = "serviceName", column = "service_name"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
    })
    List<ServiceListRequest> serviceList(Integer bookId);




    @Select("""
    SELECT DISTINCT
        b.book_id,
        bst.start_date,
        bst.end_date,
        st.status_type,
        SUM(
                CASE
                    WHEN spt.promotion_id IS NOT NULL AND bst.start_date BETWEEN p_promotion.start_date AND p_promotion.end_date  AND p_promotion.is_expired = false THEN
                        sv.service_price * (1 - p_promotion.percentage /100)
                    ELSE
                        sv.service_price
                    END
        ) AS total_amount
    FROM book_service_tb bst
             INNER JOIN book_tb b ON bst.book_id = b.book_id
             INNER JOIN service_tb sv ON bst.service_id = sv.service_id
             INNER JOIN status_tb st ON b.status_id = st.status_id
             LEFT JOIN service_promotion_tb spt ON sv.service_id = spt.service_id
             LEFT JOIN promotion_tb p_promotion ON spt.promotion_id = p_promotion.promotion_id
    WHERE bst.book_id = #{bookId}
    GROUP BY b.book_id,  bst.start_date, bst.end_date, st.status_type

    """)
    @Results(
            {
                    @Result(property = "bookId", column = "book_id"),
                    @Result(property = "serviceList", column = "book_id",
                            many = @Many(select = "serviceList")
                    ),
                    @Result(property = "status", column = "status_type"),
                    @Result(property = "amount", column = "total_amount")
            }
    )
    Book findBookById(Integer bookId);

    
    // insert into book table to return id
    @Select("INSERT INTO book_tb(status_id, user_id,shop_id) " +
            "VALUES (1, #{rq.user}, #{rq.shop}) " +
            "RETURNING book_id ")
    Integer addNewBook(@Param("rq") ServiceBookRequest serviceBookRequest);

    
    // insert into book middle table
    @Insert("""
    INSERT INTO book_service_tb (service_id, book_id, start_date, end_date) 
    VALUES (#{serviceId}, #{bookId}, #{startDate}, #{endDate})
    """)
    void saveBookService(Integer bookId, Integer serviceId, LocalDateTime startDate, LocalDateTime endDate);


    //find book by shop id
    @Select("""
    SELECT DISTINCT (bst.book_id)
    FROM book_tb b
    INNER JOIN book_service_tb bst ON b.book_id = bst.book_id
    INNER JOIN shop_tb s ON b.shop_id = s.shop_id
    WHERE bst.book_id = #{bookId}
      AND s.shop_id = #{shopId};
    """)
    Integer findBookByShopId(Integer bookId, Integer shopId);


    
    @Select("UPDATE book_tb SET status_id = #{rq.statusId}, user_id = #{rq.userId} " +
            "WHERE book_id = #{id} " +
            "RETURNING book_id ")
//    @ResultMap("mapper")
    Integer updateBook(@Param("rq") BookRequest bookRequest, Integer id);

    @Select("""
    UPDATE book_tb SET status_id = 6 WHERE book_id = #{bookId}
    RETURNING book_id
    """)
    Integer cancelBook(Integer bookId);


    //get book start date
    @Select("""
    SELECT start_date FROM book_service_tb WHERE service_id = #{serviceId}
    """)
    @Results({
            @Result(property = "startDate", column = "start_date",typeHandler = LocalDateTimeTypeHandler.class),
    })
    List<LocalDateTime> findServiceStartDate(Integer serviceId);

    //get book end date
    @Select("""
    SELECT end_date FROM book_service_tb WHERE service_id = #{serviceId}
    """)
    @Results({
            @Result(property = "endDate", column = "end_date",typeHandler = LocalDateTimeTypeHandler.class),
    })
    List<LocalDateTime> findServiceEndDate(Integer serviceId);


    //Auto update book if it ends
    @Update("""
    WITH latest_end_dates AS (
        SELECT
            service_id,
            MAX(end_date) AS latest_end_date
        FROM
            book_service_tb
        GROUP BY
            service_id
    )
    UPDATE service_tb s
    SET is_active = true
    FROM latest_end_dates led
    WHERE s.service_id = led.service_id
      AND led.latest_end_date < #{now}
    """)
    void updateStatusService(LocalDateTime now);


    //update status to busy when book
    @Update("""
    UPDATE service_tb SET is_active = false WHERE service_id = #{serviceId} AND is_active = true
    """)
    void updateStatusBusy(Integer serviceId);

    
    //get username by userid 
    @Select("""
     SELECT username FROM user_tb WHERE user_id = #{userId}
    """)
    String getUsernameByUserId(Integer userId);


    //notification
    @Select("""
    INSERT INTO notification_tb(notification_type, notification_message, notification_date, isread,reciever_id,sender_id)
    VALUES ('Book' , #{notificationMessage}, #{now} , false, #{shopId}, #{userId})
    RETURNING notification_id
    """)
    Integer insertNotification(LocalDateTime now, String notificationMessage, Integer shopId, Integer userId);

    //notification service table
    @Select("""
    INSERT INTO book_notification_tb(book_id, notification_id)
    VALUES (#{bookId}, #{notificationId})
    """)
    void insertOrderNotification(Integer bookId,Integer notificationId);

    
    // Get status id by book id
    @Select("""
    select status_id from book_tb where book_id = #{bookId}
    """)
    Integer getStatusIdByBookId(Integer bookId);

    //Update status waiting to accepted
    @Select("""
    UPDATE book_tb SET status_id = 2 WHERE shop_id = #{shopId} AND book_id = #{bookId}
    RETURNING book_id
    """)
    Integer updateBookStatus(Integer bookId, Integer shopId);


    //Update status accepted to done
    @Select("""
    UPDATE book_tb SET status_id = 5 WHERE shop_id = #{shopId} AND book_id = #{bookId}
    RETURNING book_id
    """)
    Integer updateBookStatusDone(Integer bookId, Integer shopId);

    //deny the booking
    @Select("""
    UPDATE book_tb SET status_id = 3 WHERE shop_id = #{shopId} AND book_id = #{bookId}
    RETURNING book_id
    """)
    Integer denyBooking(Integer bookId, Integer shopId);


    //get all request from shop that request
    @Select("""
    SELECT b.book_id,username, profile_img, service_name, start_date,st.status_type
    FROM book_tb b
     INNER JOIN user_tb u ON b.user_id = u.user_id
     INNER JOIN book_service_tb bst ON b.book_id = bst.book_id
     INNER JOIN service_tb s ON bst.service_id = s.service_id
    INNER JOIN status_tb st ON b.status_id = st.status_id
    WHERE shop_id = #{shopId} AND b.status_id = 1
    """)
    @Results(
            id = "bookSeller",
            value = {
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "profileImg", column = "profile_img"),
            @Result(property = "serviceName", column = "service_name"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "status", column = "status_type"),

            })
    List<BookSeller> findAllBookRequest(Integer shopId, Integer page, Integer size);



    //TEST
    @Select("""
    SELECT DISTINCT *
    FROM book_service_tb bst
     INNER JOIN book_tb b ON bst.book_id = b.book_id
     INNER JOIN service_tb sv ON bst.service_id = sv.service_id
     LEFT JOIN service_promotion_tb spt ON sv.service_id = spt.service_id
     LEFT JOIN promotion_tb p_promotion ON spt.promotion_id = p_promotion.promotion_id
    WHERE user_id = #{userId} AND status_id = 1
    """)
//    private Integer bookId;
//    private String serviceImage;
//    private String serviceName;
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
//    private String statusType;
    @Results(
            {
                    @Result(property = "bookId",column = "book_id"),
                    @Result(property = "serviceImage",column = "service_image"),
                    @Result(property = "serviceName",column = "service_name"),
                    @Result(property = "startDate",column = "start_date"),
                    @Result(property = "endDate",column = "end_date"),
                    @Result(property = "status",column = "status_id",
                        one = @One(select = "com.example.springfinalproject.repository.StatusRepository.findStatusById")
                    ),
                    @Result(property = "servicePrice", column = "service_price")
            }
    )
    List<BookTestRequest> getAllBookTestRequest(Integer userId);


    //Get all appointment that accepted
    @Select("""
    SELECT b.book_id, username, profile_img, service_name, start_date, st.status_type
     FROM book_tb b
     INNER JOIN user_tb u ON b.user_id = u.user_id
     INNER JOIN book_service_tb bst ON b.book_id = bst.book_id
     INNER JOIN service_tb s ON bst.service_id = s.service_id
     INNER JOIN status_tb st ON b.status_id = st.status_id
     WHERE shop_id = #{shopId} AND b.status_id = 2
    """)
    @ResultMap("bookSeller")
    List<BookSeller> findAllBookAccept(Integer shopId, Integer page, Integer size);


    //Get all appointment that accepted
    @Select("""
    SELECT b.book_id, username, profile_img, service_name, start_date, st.status_type
     FROM book_tb b
     INNER JOIN user_tb u ON b.user_id = u.user_id
     INNER JOIN book_service_tb bst ON b.book_id = bst.book_id
     INNER JOIN service_tb s ON bst.service_id = s.service_id
     INNER JOIN status_tb st ON b.status_id = st.status_id
     WHERE shop_id = #{shopId} AND b.status_id = 5
    """)
    @ResultMap("bookSeller")
    List<BookSeller> findAllBookDone(Integer shopId, Integer page, Integer size);

    // Deny the booking and set service active to true
    @Update("""     
    UPDATE service_tb s
    SET is_active = true
    FROM book_service_tb bst
    WHERE book_id = #{bookId}
    """)
    void updateStatusDeny(Integer bookId);

    //get All Appointment By Service Id
    @Select("""
SELECT * from book_tb b
INNER JOIN book_service_tb bst on b.book_id = bst.book_id
INNER JOIN service_tb s ON s.service_id = bst.service_id
INNER JOIN user_tb u on b.user_id = u.user_id
INNER JOIN status_tb st on b.status_id = st.status_id
where s.service_id = #{id} and shop_id = #{shopId}
""")
    @Results(
            id = "res",
            value = {
                    @Result(property = "bookId", column = "book_id"),
                    @Result(property = "username", column = "username"),
                    @Result(property = "profileImg", column = "profile_img"),
                    @Result(property = "serviceName", column = "service_name"),
                    @Result(property = "startDate", column = "start_date"),
                    @Result(property = "status",column = "status_id")
            })
    List<BookSeller> getAllAppointmentByServiceId(Integer id, Integer shopId);

    //Get all history customer
    @Select("""
    
            SELECT
        u.username,
        u.email,
        s.service_image,
        b.book_id,
        bst.start_date,
        st.status_type,
        sh.shop_type_id,
        SUM(
                CASE
                    WHEN spt.promotion_id IS NOT NULL AND b_promotion.is_expired = false THEN
                        s.service_price * (1 - b_promotion.percentage / 100)
                    ELSE
                        s.service_price
                    END
        ) AS total_amount
    FROM
        book_tb b
            INNER JOIN user_tb u ON b.user_id = u.user_id
            INNER JOIN book_service_tb bst ON b.book_id = bst.book_id
            INNER JOIN service_tb s ON bst.service_id = s.service_id
            INNER JOIN status_tb st ON b.status_id = st.status_id
            INNER JOIN shop_tb sh ON b.shop_id = sh.shop_id
            LEFT JOIN service_promotion_tb spt ON s.service_id = spt.service_id
            LEFT JOIN promotion_tb b_promotion ON spt.promotion_id = b_promotion.promotion_id
    WHERE
        b.user_id = #{userId} AND b.status_id = 5
    GROUP BY
        u.username,
        u.email,
        s.service_image,
        b.book_id,
        bst.start_date,
        st.status_type,
        sh.shop_type_id
    """)
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "serviceImage", column = "service_image"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "status", column = "status_type"),
            @Result(property = "shopTypeId", column = "shop_type_id"),
            @Result(property = "totalAmount", column = "total_amount"),
    })
    List<BookHistoryRequest> findAllBookHistoryCustomer(Integer userId);
}
